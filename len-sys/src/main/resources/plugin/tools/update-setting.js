(function ($) {
    $.fn.disable = function () {
        return $(this).find("*").each(function () {
            $(this).attr("disabled", "disabled");
        });
    }
})(jQuery);