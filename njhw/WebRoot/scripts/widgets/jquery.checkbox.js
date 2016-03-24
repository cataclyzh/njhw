(function($) {
	$.fn.chk_init = function(options) {
		$(options.all).click(function() {
			$(this).checkAll(options);
		});
		$(options.item).click(function() {
			if($(options.item+":checked").size()==$(options.item).size()){
				$(options.all)[0].checked=true;
			}else{
				$(options.all)[0].checked=false;
			}
		});
	};
	$.fn.checkAll = function(options) {
		$(options.item).each(function(i) {
			this.checked = $(options.all)[0].checked;
		});
	};
})(jQuery);