#!/usr/bin/env python
# coding: utf-8

import pandas as pd
import glob
import seaborn as sns
import matplotlib.pyplot as plt
get_ipython().run_line_magic('matplotlib', 'inline')

import matplotlib.font_manager as fm
import re



# ## 좋아요(평점) 분석

# In[ ]:


review_keywords = pd.read_pickle('./review_keywords.p')


# In[25]:


likes = pd.read_csv('./like.csv')


# In[26]:


# 데이터프레임 병합
likes = likes.merge(review_keywords, left_on='placeId', right_index=True)


# In[27]:


likes = likes.merge(place_info, left_on='placeId', right_index=True)


# In[28]:


# '친절도' 열을 3번째 열로 이동
likes.insert(3, 'name', likes.pop('name'))


# In[29]:


likes.to_pickle('./likes_update.p')
likes = pd.read_pickle('./likes_update.p')


# ## Test Train Split

# In[30]:


from sklearn.model_selection import train_test_split


# In[31]:


train, test = train_test_split(likes, random_state=42, test_size=.1)


# In[32]:


from sklearn.model_selection import RandomizedSearchCV
from scipy.stats import uniform as sp_rand
from sklearn.linear_model import Lasso


# In[35]:


reviews_cols=review_keywords.columns


# ## 전체 user

# In[36]:


review_keywords = pd.read_pickle('./reviews.p')


# In[37]:


from sklearn.linear_model import LinearRegression


# In[38]:


from sklearn.linear_model import Lasso
from sklearn.preprocessing import StandardScaler
from sklearn.model_selection import GridSearchCV

# 전체 데이터에서 최적의 alpha 값 찾기
x_train_all = train[review_keywords.columns]
y_train_all = train['like']

# 특성 정규화
scaler = StandardScaler()
x_train_all_scaled = scaler.fit_transform(x_train_all)

# GridSearchCV를 이용한 하이퍼파라미터 튜닝
alpha_range = {'alpha': [0.01, 0.1, 1, 10, 100]}
lasso = Lasso()
rsearch = GridSearchCV(lasso, alpha_range, cv=5)
rsearch.fit(x_train_all_scaled, y_train_all)
alpha = rsearch.best_estimator_.alpha

# 사용자별로 모델 학습
user_profile_list = []

for userId in train['userid'].unique():
    user = train[train['userid'] == userId]
    x_train = user[review_keywords.columns]  # 특성
    y_train = user['like']  # 라벨

    # 각 사용자 데이터 정규화
    x_train_scaled = scaler.transform(x_train)

    # 최적 alpha로 Lasso 모델 학습
    reg = Lasso(alpha=alpha)
    reg.fit(x_train_scaled, y_train)

    user_profile_list.append([reg.intercept_, *reg.coef_])


# In[39]:


user_profiles = pd.DataFrame(user_profile_list, index=train['userid'].unique(), # user를 인덱스로
                            columns=['intercept', *review_keywords.columns])


# In[40]:


user_profile_lasso = pd.DataFrame(user_profile_list,
                            index=train['userid'].unique(),
                            columns=['intercept', *review_keywords.columns])


# In[41]:


from tqdm import tqdm_notebook


# In[42]:


# 평점 예측
predict = []

for idx, row in tqdm_notebook(test.iterrows()):
  user = row['userid'] # test row에 user 데이터가 들어옴

  # 해당 user의 profile에서 intercept 값을 가져옴
  intercept = user_profile_lasso.loc[user, 'intercept']

  # 해당 장소의 카테고리에서 비롯되는 예상 점수
  category_score = sum(user_profile_lasso.loc[user, review_keywords.columns] * row[review_keywords.columns])
  expected_score = intercept + category_score
  predict.append(expected_score)


# In[43]:


test['predict_lasso'] = predict


# In[44]:


# DataFrame에서 마지막 열을 선택하여 Series로 가져옵니다.
last_column = test[test.columns[-1]]

# 마지막 열을 제외한 열들을 선택합니다.
other_columns1 = test[test.columns[5:-1]]
other_columns2 = test[test.columns[0:4]]

# 원하는 순서로 열을 재배열하여 새로운 DataFrame을 생성합니다.
new_df = pd.concat([other_columns2, last_column,other_columns1], axis=1)


# In[45]:


final_df = new_df[(new_df['userid']==5) & (new_df['like']==1)].sort_values(by='predict_lasso', ascending=False).head(1)


# In[46]:


selected = ['userid', 'placeId', 'name','address', 'predict_lasso']


# In[47]:


final_df = final_df[selected]


# ## 평가지표

# In[48]:


from sklearn.metrics import mean_squared_error
import numpy as np


# In[49]:


rmse = np.sqrt(mean_squared_error(test['like'], test['predict_lasso']))


# In[50]:


intercept = reg.intercept_
coef = reg.coef_


# ## Spring과 연동

# In[51]:


# 설치가 필요할 경우
# !pip install Flask

from flask import Flask, jsonify, Response


# In[ ]:


app = Flask(__name__)

@app.route('/data', methods=['GET'])
def get_data():
    data_json = final_df.to_json(orient='records', force_ascii=False) # 인코딩 오류 방지
    return Response(data_json, content_type='application/json; charset=utf-8')

if __name__ == '__main__':
    app.run(port=5000)


# In[ ]:




