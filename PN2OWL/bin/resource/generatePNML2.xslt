<?xml version="1.0" encoding="UTF-8"?>
<xsl:transform version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml" omit-xml-declaration="no" indent="yes" />

<xsl:template match="/student">
<student id="" style="">
	<xsl:attribute name="id">
		<xsl:value-of select="/student/@id"/>
	</xsl:attribute>
	<name>
		<xsl:attribute name="name"><xsl:value-of select="/student/name"/></xsl:attribute>
	</name>
	<age>
		<xsl:attribute name="age"><xsl:value-of select="/student/age"/></xsl:attribute>
	</age>
</student>
</xsl:template>

</xsl:transform>