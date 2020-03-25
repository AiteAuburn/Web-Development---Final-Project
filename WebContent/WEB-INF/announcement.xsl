<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="html" indent="yes"/>
<xsl:template match="announcementList">
  <div class="announcementList">
    <xsl:for-each select="announcement">
      <xsl:variable name="i" select="position()"/>
      <div class="announcement">
        <div class="title"><xsl:value-of select="title" /></div>
        <div class="time"><xsl:value-of select="time" /></div>
        <div class="content"><xsl:value-of select="content" /></div>
      </div>
    </xsl:for-each>
  </div>
</xsl:template>
</xsl:stylesheet>