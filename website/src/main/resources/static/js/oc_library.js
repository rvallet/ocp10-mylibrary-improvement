//Focus Anchor in URL
var url = document.location.toString();
if (url.match('#')) {
	$('.anchor a[href="#'+url.split('#')[1]+'"]').tab('show');
}