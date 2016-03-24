$(function() {
	$(
			"input[type=submit],input[type=button],input[type=reset], input[type=file],a, button")
			.button().click(function(event) {
			});
	var progressbar = $("#progressbar"), progressLabel = $(".progress-label");

	progressbar.progressbar({
		value : false,

	});

});
$(window).resize(function() {
});
