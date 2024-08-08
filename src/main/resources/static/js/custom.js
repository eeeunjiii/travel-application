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
function toggleHeart(element) {
  if (element.textContent === '🩶') {
    element.textContent = '❤️'; // 채운 하트로 변경

  } else {
    element.textContent = '🩶'; // 빈 하트로 변경

  }
}

document.querySelectorAll('.trip-item').forEach(item => {
  item.addEventListener('click', function() {
    // 현재 선택된 요소
    const selected = document.querySelector('.trip-item.selected');

    // 이미 선택된 요소가 있고, 현재 클릭된 요소가 선택된 요소가 아닌 경우
    if (selected && selected !== this) {
      selected.classList.remove('selected');
    }

    // 현재 클릭된 요소를 선택된 상태로 설정
    this.classList.toggle('selected', !this.classList.contains('selected'));
  });
});

//예산 조정 바
const rangeSlider = document.getElementById('budget');
const rangeValue = document.getElementById('rangeValue');

rangeSlider.addEventListener('input', function() {
    rangeValue.textContent = rangeSlider.value;
});

//지역
function loadSubregions() {
    var city = $('#city').val();
    $.ajax({
        url: '/user-plans/districts',
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