#!/usr/bin/env python
# coding: utf-8

# In[1]:


import pandas as pd
import glob
import seaborn as sns
import matplotlib.pyplot as plt
get_ipython().run_line_magic('matplotlib', 'inline')

import matplotlib.font_manager as fm


# In[2]:


restaurants = pd.read_csv('./pre_complete.csv')
restaurants = restaurants.transpose()


# In[3]:


restaurants.columns = restaurants.iloc[0]


# In[4]:


restaurants.columns.name = None


# In[5]:


# 첫 번째 열의 내용에서 'index'와 그 뒤에 오는 숫자만 추출하여 새로운 인덱스로 설정
restaurants.index.name = '관광타입'


# ## 장소 이름 분석

# In[6]:


names = list(restaurants['음식점명'])


# In[7]:


address = list(restaurants['주소'])


# In[8]:


names=pd.DataFrame(names)
address = pd.DataFrame(address)


# In[9]:


names.rename_axis('Id', inplace=True)
address.rename_axis('id', inplace=True)


# In[10]:


# '친절해요' 열의 이름을 '친절도'로 변경
names.rename(columns={0: 'name'}, inplace=True)
address.rename(columns={0:'address'}, inplace=True)


# In[11]:


place_info = pd.merge(names, address, left_index=True, right_index=True)


# In[12]:


place_info = place_info.drop(index=0)


# In[13]:


place_info.to_pickle('./place_info.p')
place_info = pd.read_pickle('./place_info.p')


# ## 리뷰 키워드 분석

# In[14]:


# NaN을 빈 문자열로 대체
restaurants['리뷰키워드'].fillna('없음', inplace=True)  # '리뷰키워드'는 해당 열의 이름으로 대체해주세요.


# In[15]:


import re


# In[16]:


review_keywords =restaurants['리뷰키워드'].apply(lambda x: re.sub(r'\s+', '', x))
review_keywords = review_keywords.apply(lambda x: x.replace(".", ","))
review_keywords = review_keywords.apply(lambda x: x.split(","))
#review_keywords = list(review_keywords.apply(lambda x: x.split(",")))
review_keywords
# '리뷰키워드' 열의 값에 대해 split 적용
#review_keywords = list(restaurants['리뷰키워드'].apply(lambda x: x.split("," | ", ")))
#review_keywords


# In[17]:


flat_list = []
for sublist in review_keywords:
  for item in sublist:
    flat_list.append(item)


# In[18]:


review_keywords_unique = pd.Series(list(set(flat_list)))


# In[19]:


#restaurants['리뷰키워드'] = restaurants['리뷰키워드'].str.replace(r'\s+', '')
restaurants['리뷰키워드'] = restaurants['리뷰키워드'].str.replace('.', ',')

restaurants['리뷰키워드'] = restaurants['리뷰키워드'].str.replace(r'\s+', '', regex=True)


# In[20]:


review_keywords_dummies = restaurants['리뷰키워드'].str.get_dummies(sep=',')


# In[21]:


review_keywords_dummies.to_pickle('./reviews.p')
review_keywords = pd.read_pickle('./reviews.p')


# In[22]:


restaurants['id'] = range(1, len(restaurants)+1)


# In[23]:


restaurants.set_index('id', inplace=True)


# In[24]:


review_keywords['id'] = range(1, len(review_keywords)+1)
review_keywords.set_index('id', inplace=True)


# In[ ]:


review_keywords.to_pickle('./review_keywords.p')

