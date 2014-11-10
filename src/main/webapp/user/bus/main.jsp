<%@ page contentType="text/html;charset=gb2312"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]>	<html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]>	<html class="no-js lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]> <html class="no-js ie9" lang="en"> <!-->
<html lang="en">
<!--<![endif]-->
<head>
<!-- Set the viewport width to device width for mobile -->
<meta name="viewport" content="width=device-width" />
<title>主页</title>

<s:include value="/jsps/common/head.jsp" />

</head>
<body>
	
	<header id="brand">
	<div class="container">
		<div class="row">
			<div class="appname hide-on-phones">跟踪助手</div>
			<address>
				<span> <strong></strong>欢迎用户， <s:property value="name" /><a href="user/logoff.ls?username=<s:property value="username" />" class="login-secure line">退出登陆</a>
				</span>
			</address>
		</div>
	</div>
</header>
	<s:include value="/jsps/common/menu.jsp" />
	<section class="mainbg">
		<div class="container">
			<div class="row">
				<div class="app-wrapper ui-corner-top">
					<div class="blue module ui-corner-top clearfix">
						<h2>江</h2>
					</div>
					<div class="content">
						
					</div>
				</div>
			</div>
		</div>
	</section>
	<s:include value="/jsps/common/footer.jsp" />

	<script>
		$(document).ready(function() {
		});
	</script>
</body>
</html>
