function showphone(cityLocal, dispCategory) {
	var pn = 'C29C0040637C187E41C97E412398A6D8A';
	var _en = pn.indexOf("_") == 0 ? pn.replace('_', '').split("_") : pn
			.split("_");
	var _img = $("img[name='phlink']");
	$.each(_img, function(index, img) {
		img.src = img.src + _en[index];
	});
	$("#showphone").show();
	$("#telbtn").hide();
	var _img = $("img[name='plink']")
	$.each(_img, function(index, img) {
		img.src = img.src + _en[index];
	});
	$("#ck2").show();
	$("#ck1").hide();
};