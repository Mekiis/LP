$(document).ready(function () {
    
    $('#menuCatalogue').click(function () {
        console.log("hello");
       
        if ($('#catalogueMore').css('display') === 'block') {
            $('#catalogueMore').css('display', 'none');
            $('#promotionMore').css('display', 'none');
            $('#boutiqueMore').css('display', 'none');
        } else {
            $('#catalogueMore').css('display', 'block');
            $('#promotionMore').css('display', 'none');
            $('#boutiqueMore').css('display', 'none');
        }
    });
    $('#menuPromotion').click(function () {
        console.log("hello");
        if ($('#promotionMore').css('display') === 'block') {
            $('#catalogueMore').css('display', 'none');
            $('#promotionMore').css('display', 'none');
            $('#boutiqueMore').css('display', 'none');
        } else {
            $('#catalogueMore').css('display', 'none');
            $('#promotionMore').css('display', 'block');
            $('#boutiqueMore').css('display', 'none');
        }
    });
    $('#menuBoutique').click(function () {
        console.log("hello");
        if ($('#boutiqueMore').css('display') === 'block') {
            $('#catalogueMore').css('display', 'none');
            $('#promotionMore').css('display', 'none');
            $('#boutiqueMore').css('display', 'none');
        } else {
            $('#catalogueMore').css('display', 'none');
            $('#promotionMore').css('display', 'none');
            $('#boutiqueMore').css('display', 'block');
        }
    });
    

});