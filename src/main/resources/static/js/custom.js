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
                <img src="카카오맵에서 가져온 사진" alt="여행지 사진">
                <h5>${recommendation.name}</h5>
                <p><a href="카카오맵 장소 설명으로 이동">상세 설명 보기</a></p>
            </a>
        `;
        container.appendChild(placeItem);
    });

    // 추천 결과도 선택 가능하도록 수정
    document.querySelectorAll('.place-item').forEach(item => {
        item.addEventListener('click', function() {
            const selected = document.querySelector('.place-item.selected');
            if (selected && selected !== this) {
                selected.classList.remove('selected');
            }
            this.classList.toggle('selected', !this.classList.contains('selected'));
        });
    });
}

// 여행정보 제출 시 추천 데이터 가져오기
//document.addEventListener('DOMContentLoaded', initializePage);



function toggleHeart(element) {
  if (element.textContent === '🩶') {
    element.textContent = '❤️'; // 채운 하트로 변경
  } else {
    element.textContent = '🩶'; // 빈 하트로 변경
  }
}


