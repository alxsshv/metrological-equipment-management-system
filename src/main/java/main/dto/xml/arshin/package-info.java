@XmlSchema(
        namespace = "urn://fgis-arshin.gost.ru/module-verifications/import/2020-06-19",
        elementFormDefault = XmlNsForm.QUALIFIED,
        xmlns = {
                @XmlNs(prefix="gost", namespaceURI="urn://fgis-arshin.gost.ru/module-verifications/import/2020-06-19")
        }
)

package main.dto.xml.arshin;
import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlNsForm;
import jakarta.xml.bind.annotation.XmlSchema;