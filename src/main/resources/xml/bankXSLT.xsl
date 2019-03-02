<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"

                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <body style="font-family: Arial; font-size: 12pt; ">
                <div style="color: red; font-style:italic;">
                    <h2>Hotels</h2>
                </div>
                <table border="3">
                    <tr bgcolor="yellow">
                        <th>Name</th>
                        <th>Country</th>
                    </tr>

                    <xsl:for-each select="banks/bank">

                        <xsl:sort select="country" order=""/>
                        <tr>
                            <td><xsl:value-of select="name"/></td>
                            <td><xsl:value-of select="country"/></td>
                        </tr>

                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
