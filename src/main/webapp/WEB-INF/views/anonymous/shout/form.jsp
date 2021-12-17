<%--
- form.jsp
-
- Copyright (C) 2012-2021 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="anonymous.shout.form.label.author" path="author" placeholder="John Doe"/>
	<acme:form-textarea code="anonymous.shout.form.label.text" path="text" placeholder="Lorem ipsum!"/>
	<acme:form-url code="anonymous.shout.form.label.info" path="info"/>
	
	<acme:footer-subpanel code="anonymous.solim">
		<acme:form-textbox code="anonymous.solim.form.label.keylet" path="solim.keylet" placeholder="yymmdd#(d{1,3})"/>
		<acme:form-moment code="anonymous.solim.form.label.deadline" path="solim.deadline" placeholder="2000/01/01 00:00"/>
		<acme:form-money code="anonymous.solim.form.label.budget" path="solim.budget" placeholder="EUR/USD/GBP 31592.19"/>
		<acme:form-checkbox code="anonymous.solim.form.label.important" path="solim.important"/>
	</acme:footer-subpanel>
	
	<acme:form-submit code="anonymous.shout.form.button.create" action="/anonymous/shout/create"/>
  	<acme:form-return code="anonymous.shout.form.button.return"/>
</acme:form>
