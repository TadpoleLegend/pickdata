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
<title>抓取</title>

<s:include value="/jsps/common/head.jsp" />

</head>
<body>
	<header id="brand">
		<div class="container">
			<div class="row">
				<div class="appname hide-on-phones">小店助手</div>
			</div>
		</div>
	</header>

	<s:include value="/jsps/common/menu.jsp" />
	<section class="mainbg">
		<div class="container">
			<div class="row">
				<div class="app-wrapper ui-corner-top">
					<div class="blue module ui-corner-top clearfix">
						<h2>抓取任务</h2>
					</div>
					<div class="content">
						<div class="row">
							<div class="four columns">
								<form>
									<fieldset>
										<legend>江苏</legend>
										<div class="row">
											<div class="ten columns">
												<label class="input-checkbox" for="checkbox-2"> <input
													type="checkbox" id="checkbox-2" name="checkbox-group"><b>全选</b>
												</label> <label class="input-checkbox" for="checkbox-2"> <input
													type="checkbox" id="checkbox-2" name="checkbox-group">苏州
												</label>
											</div>
										</div>

									</fieldset>
								</form>
							</div>
							<div class="four columns">
								<form>
									<fieldset>
										<legend>浙江</legend>
										<div class="row">
											<div class="ten columns">
												<label class="input-checkbox" for="checkbox-2"> <input
													type="checkbox" id="checkbox-2" name="checkbox-group"><b>全选</b>
												</label> <label class="input-checkbox" for="checkbox-2"> <input
													type="checkbox" id="checkbox-2" name="checkbox-group">苏州
												</label>
											</div>
										</div>

									</fieldset>
								</form>
							</div>
							<div class="four columns">
								<form>
									<fieldset>
										<legend>安徽</legend>
										<div class="row">
											<div class="ten columns">
												<label class="input-checkbox" for="checkbox-2"> <input
													type="checkbox" id="checkbox-2" name="checkbox-group"><b>全选</b>
												</label> <label class="input-checkbox" for="checkbox-2"> <input
													type="checkbox" id="checkbox-2" name="checkbox-group">苏州
												</label>
											</div>
										</div>

									</fieldset>
								</form>
							</div>
						</div>
						<a class="nice radius blue button" href="">开始抓取</a>
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
