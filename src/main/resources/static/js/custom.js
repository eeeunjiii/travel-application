$(window).on('load', function(){
    "use strict";
    /*=========================================================================
            Preloader
    =========================================================================*/
    $("#preloader").delay(750).fadeOut('slow');
});

/*=========================================================================
            Home Slider
=========================================================================*/
$(document).ready(function() {
    "use strict";

    /*=========================================================================
            Slick sliders
    =========================================================================*/
    $('.post-carousel-lg').slick({
      dots: true,
      arrows: true,
      slidesToShow: 1,
      slidesToScroll: 1,
      fade: true,
      cssEase: 'linear',
      responsive: [
        {
          breakpoint: 768,
          settings: {
            slidesToShow: 1,
            slidesToScroll: 1,
            dots: true,
            arrows: false,
          }
        }
      ]
    });

    $('.post-carousel-featured').slick({
      dots: true,
      arrows: false,
      slidesToShow: 5,
      slidesToScroll: 2,
      responsive: [
        {
          breakpoint: 1440,
          settings: {
            slidesToShow: 4,
            slidesToScroll: 4,
            dots: true,
            arrows: false,
          }
        },
        {
          breakpoint: 1024,
          settings: {
            slidesToShow: 3,
            slidesToScroll: 3,
            dots: true,
            arrows: false,
          }
        },
        {
          breakpoint: 768,
          settings: {
            slidesToShow: 2,
            slidesToScroll: 2,
            dots: true,
            arrows: false,
          }
        }
        ,
        {
          breakpoint: 576,
          settings: {
            slidesToShow: 1,
            slidesToScroll: 1,
            dots: true,
            arrows: false,
          }
        }
      ]
    });

    $('.post-carousel-twoCol').slick({
      dots: false,
      arrows: false,
      slidesToShow: 2,
      slidesToScroll: 1,
      responsive: [
        {
          breakpoint: 768,
          settings: {
            slidesToShow: 2,
            slidesToScroll: 2,
            dots: false,
            arrows: false,
          }
        },
        {
          breakpoint: 576,
          settings: {
            slidesToShow: 1,
            slidesToScroll: 1,
            dots: false,
            arrows: false,
          }
        }
      ]
    });
    // Custom carousel nav
    $('.carousel-topNav-prev').click(function(){ 
      $('.post-carousel-twoCol').slick('slickPrev');
    } );
    $('.carousel-topNav-next').click(function(){ 
      $('.post-carousel-twoCol').slick('slickNext');
    } );


    $('.post-carousel-widget').slick({
      dots: false,
      arrows: false,
      slidesToShow: 1,
      slidesToScroll: 1,
      responsive: [
        {
          breakpoint: 991,
          settings: {
            slidesToShow: 2,
            slidesToScroll: 1,
          }
        },
        {
          breakpoint: 576,
          settings: {
            slidesToShow: 1,
            centerMode: true,
            slidesToScroll: 1,
          }
        }
      ]
    });
    // Custom carousel nav
    $('.carousel-botNav-prev').click(function(){ 
      $('.post-carousel-widget').slick('slickPrev');
    } );
    $('.carousel-botNav-next').click(function(){ 
      $('.post-carousel-widget').slick('slickNext');
    } );

    /*=========================================================================
            Sticky header
    =========================================================================*/
    var $header = $(".header-default, .header-personal nav, .header-classic .header-bottom"),
      $clone = $header.before($header.clone().addClass("clone"));

    $(window).on("scroll", function() {
      var fromTop = $(window).scrollTop();
      $('body').toggleClass("down", (fromTop > 300));
    });

});

$(function(){
    "use strict";

    /*=========================================================================
            Sticky Sidebar
    =========================================================================*/
    $('.sidebar').stickySidebar({
        topSpacing: 60,
        bottomSpacing: 30,
        containerSelector: '.main-content',
    });

    /*=========================================================================
            Vertical Menu
    =========================================================================*/
    $( ".submenu" ).before( '<i class="icon-arrow-down switch"></i>' );

    $(".vertical-menu li i.switch").on( 'click', function() {
        var $submenu = $(this).next(".submenu");
        $submenu.slideToggle(300);
        $submenu.parent().toggleClass("openmenu");
    });

    /*=========================================================================
            Canvas Menu
    =========================================================================*/
    $("button.burger-menu").on( 'click', function() {
      $(".canvas-menu").toggleClass("open");
      $(".main-overlay").toggleClass("active");
    });

    $(".canvas-menu .btn-close, .main-overlay").on( 'click', function() {
      $(".canvas-menu").removeClass("open");
      $(".main-overlay").removeClass("active");
    });

    /*=========================================================================
            Popups
    =========================================================================*/
    $("button.search").on( 'click', function() {
      $(".search-popup").addClass("visible");
    });

    $(".search-popup .btn-close").on( 'click', function() {
      $(".search-popup").removeClass("visible");
    });

    $(document).keyup(function(e) {
          if (e.key === "Escape") { // escape key maps to keycode `27`
            $(".search-popup").removeClass("visible");
        }
    });

    /*=========================================================================
            Tabs loader
    =========================================================================*/
    $('button[data-bs-toggle="tab"]').on( 'click', function() {
      $(".tab-pane").addClass("loading");
      $('.lds-dual-ring').addClass("loading");
      setTimeout(function () {
          $(".tab-pane").removeClass("loading");
          $('.lds-dual-ring').removeClass("loading");
      }, 500);
    });
    
    /*=========================================================================
            Social share toggle
    =========================================================================*/
    $('.post button.toggle-button').each( function() {
      $(this).on( 'click', function(e) {
        $(this).next('.social-share .icons').toggleClass("visible");
        $(this).toggleClass('icon-close').toggleClass('icon-share');
      });
    });

    /*=========================================================================
    Spacer with Data Attribute
    =========================================================================*/
    var list = document.getElementsByClassName('spacer');

    for (var i = 0; i < list.length; i++) {
      var size = list[i].getAttribute('data-height');
      list[i].style.height = "" + size + "px";
    }

    /*=========================================================================
    Background Image with Data Attribute
    =========================================================================*/
    var list = document.getElementsByClassName('data-bg-image');

    for (var i = 0; i < list.length; i++) {
      var bgimage = list[i].getAttribute('data-bg-image');
      list[i].style.backgroundImage  = "url('" + bgimage + "')";
    }

});


/*=========================================================================
            상시 사용 함수
 =========================================================================*/
 let selectedPlaces = [];

document.addEventListener('DOMContentLoaded', function() {
    fetchRecommendations();
});

async function sendRequest(url, data, methodType){
    try {
        const response = await fetch(url, {
            method: methodType,
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const result = await response.json();
        console.log('Server response:', result);
    } catch (error) {
        console.error('Error:', error);
    }
}

/*=========================================================================
            USER PLAN 제작 관련
 =========================================================================*/

 // 예산 상관 없음
 function toggleInput() {
             var budgetInput = document.getElementById('budgetInput');
             var budgetNoLimit = document.getElementById('budgetNoLimit');
             var budgetDirectInput = document.getElementById('budgetDirectInput');
             var budgetHidden = document.getElementById('budgetHidden');
             if(budgetDirectInput.checked) {
                 budgetInput.style.display='inline';
                 budgetHidden.value='';
             } else if(budgetNoLimit.checked) {
                 budgetInput.style.display='none';
                 budgetHidden.value=9999999;
             }
         }

//오늘 날짜 가져오기
//document.addEventListener('DOMContentLoaded', function() {
//    const dateElement = document.getElementById('current-date');
//    const today = new Date();
//
//    // 29 March 2021
//    const options = { year: 'numeric', month: 'long', day: 'numeric' };
//    const formattedDate = today.toLocaleDateString('en-US', options);
//
//    dateElement.textContent = formattedDate;
//    fetchRecommendations();
//});

 document.addEventListener('DOMContentLoaded', function() {
            var today = new Date().toISOString().split('T')[0];
            document.getElementById('startDate').setAttribute('min', today);

            document.getElementById('startDate').addEventListener('change', function() {
                var startDate = this.value;
                document.getElementById('endDate').setAttribute('min', startDate);
            });
 });


//예산 조정 바
//const rangeSlider = document.getElementById('budget');
//const rangeValue = document.getElementById('rangeValue');
//
//rangeSlider.addEventListener('input', function() {
//    rangeValue.textContent = rangeSlider.value;
//});

function toggleInput() {
     var budgetInput = document.getElementById('budgetInput');
     var budgetNoLimit = document.getElementById('budgetNoLimit');
     var budgetDirectInput = document.getElementById('budgetDirectInput');
     var budgetHidden = document.getElementById('budgetHidden');
     if(budgetDirectInput.checked) {
          budgetInput.style.display='inline';
          budgetHidden.value='';
            } else if(budgetNoLimit.checked) {
                budgetInput.style.display='none';
                budgetHidden.value=9999999;
            }
        }
        function updateRangeValue() {
            var rangeSlider = document.getElementById('budget');
            var rangeValue = document.getElementById('rangeValue');
            var budgetHidden = document.getElementById('budgetHidden');
            rangeValue.textContent = rangeSlider.value;
            budgetHidden.value = rangeSlider.value;
        }
        window.onload = function() {
            toggleInput();
}

//지역
function loadSubregions() {
    var city = $('#city').val();
    $.ajax({
        url: '/user-plan/districts',
        type: 'GET',
        data: { city: city },
        success: function(data) {
            $('#district').empty();
            $('#district').append('<option value="">==시 · 군 · 구 선택==</option>');
            data.forEach(function(district) {
                $('#district').append('<option value="' + district + '">' + district + '</option>');
            });
        }
    });
}


//추천 장소 데이터 가져오기
async function fetchRecommendations() {
    const response = await fetch('/recommendations');
    const recommendations = await response.json();
    console.log('Fetched recommendations:', recommendations);


    const container = document.getElementById('recom-list');
    container.innerHTML = ''; // 기존 요소들을 지우고 새로운 것들로 대체

    recommendations.forEach((recommendation, index) => {
        const placeItem = document.createElement('div');
        placeItem.className = 'place-item';
        placeItem.innerHTML = `
            <a>
                <div class="heart-icon" onclick="toggleHeart(this)">🩶</div>
                <img src="카카오맵에서 가져온 사진" alt="${recommendation.placeId}">
                <h5>${recommendation.name}</h5>
                <p><a href="카카오맵 장소 설명으로 이동">${recommendation.address}</a></p>
            </a>
        `;
        container.appendChild(placeItem);
    });

    // 추천 결과도 선택에 포함
    selectPlaces();
}


function selectPlaces(){

    document.querySelectorAll('.place-item').forEach(item => {
        item.addEventListener('click', function() {
            const placeId = this.querySelector('img').getAttribute('alt')
            const selectedIndex = selectedPlaces.indexOf(placeId);

            if (selectedIndex > -1) {
                selectedPlaces.splice(selectedIndex, 1); // selectedIndex로부터 1개 항목 제거
                this.classList.remove('selected');
                const numberSpan = this.querySelector('.selection-index');
                if (numberSpan) numberSpan.remove();
            } else {
                if (selectedPlaces.length < 6) { // 최대 6개까지만 선택 가능
                    selectedPlaces.push(placeId);
                    this.classList.add('selected');
                    let numberSpan = document.createElement('span');
                    numberSpan.classList.add('selection-index');
                    this.prepend(numberSpan);
                }
            }

            // 선택된 순서에 따라 번호 매기기
            selectedPlaces.forEach((alt, index) => {
                const placeItem = Array.from(document.querySelectorAll('.place-item')).find(item =>
                item.querySelector('img').getAttribute('alt') === alt
                );

                if (placeItem) {
                    let numberSpan = placeItem.querySelector('.selection-index');
                    if (!numberSpan) {
                        numberSpan = document.createElement('span');
                        numberSpan.classList.add('selection-index');
                        placeItem.prepend(numberSpan);
                    }
                    numberSpan.textContent = `${index + 1}`;
                }
            });
        });
    });

}

// 선택한 장소 저장
document.getElementById('saveButton').addEventListener('click', function() {
    const userPlanId = document.getElementById('userPlanId').value;

    console.log('선택된 장소 IDs:', selectedPlaces);
    sendRequest('/user-plan/save-places', selectedPlaces, 'POST');

});



/*=========================================================================
            장소 좋아요 관련
 =========================================================================*/

function toggleHeart(heartElement) {
    var placeId=$(heartElement).data('place-id');
    var isLiked=$(heartElement).hasClass('liked');

    $.ajax({
        url: `/places/like/${placeId}`,
        type: 'POST',
        success: function(isNowLiked) {
            if(isNowLiked) {
                if(isLiked) {
                    $(heartElement).removeClass('liked').addClass('not-liked').text('🩶');
                } else {
                    $(heartElement).removeClass('not-liked').addClass('liked').text('❤️');
                }
                $(heartElement).removeClass('not-liked').addClass('liked').text('❤️');
            } else {
                if(isLiked) {
                    $(heartElement).removeClass('liked').addClass('not-liked').text('🩶');
                } else {
                    $(heartElement).removeClass('not-liked').addClass('liked').text('❤️');
                }
            }
        },
        error: function() {
            alert('장소 좋아요 처리에 실패했습니다.');
        }
    });
}


/*=========================================================================
                                PLAN
 =========================================================================*/
$(document).on('click', '.bookmark-icon', function() {
    var planId=$(this).data('plan-id');
    var $icon=$(this);
    var isCurrentlySaved = $icon.hasClass('fas');

    $.ajax({
        url: `/plans/community/save/${planId}`,
        type: 'POST',
        success: function(isSaved) {
            if (isSaved) {
                if (isCurrentlySaved) {
                    $icon.removeClass('fas fa-bookmark').addClass('far fa-bookmark');
                } else {
                    $icon.removeClass('far fa-bookmark').addClass('fas fa-bookmark');
                }
            } else {
                if (isCurrentlySaved) {
                    $icon.removeClass('fas fa-bookmark').addClass('far fa-bookmark');
                } else {
                    $icon.removeClass('far fa-bookmark').addClass('fas fa-bookmark');
                }
            }
        },
        error: function() {
            alert('일정을 저장하는 데 실패했습니다');
        }
    });
});
