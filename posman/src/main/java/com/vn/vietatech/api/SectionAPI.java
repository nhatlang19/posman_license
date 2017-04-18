package com.vn.vietatech.api;

import android.content.Context;

import com.vn.vietatech.model.Section;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class SectionAPI extends AbstractAPI {

    public SectionAPI(Context context) throws Exception {
        super(context);
    }

    public ArrayList<Section> getSection() throws Exception {
        setMethod(METHOD_GET_SECTION);
        ArrayList<Section> sections = new ArrayList<Section>();

        SoapObject response = (SoapObject) this.callService();
        SoapObject soapObject = (SoapObject) ((SoapObject) response).getProperty("diffgram");
        if (soapObject != null) {
            SoapObject webServiceResponse = (SoapObject) soapObject
                    .getProperty("NewDataSet");
            for (int i = 0; i < webServiceResponse.getPropertyCount(); i++) {
                SoapObject table = (SoapObject) webServiceResponse
                        .getProperty(i);

                Section section = new Section();
                section.setId(table.getProperty("Section").toString());
                section.setName(table.getProperty("Description1").toString());
                section.setRevCtr(table.getProperty("RevCtr").toString());

                sections.add(section);
            }
        }
        return sections;
    }
}
