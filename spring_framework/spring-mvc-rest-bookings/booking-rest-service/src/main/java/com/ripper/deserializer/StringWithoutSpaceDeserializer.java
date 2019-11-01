package com.ripper.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class StringWithoutSpaceDeserializer extends StdDeserializer<String> {

    /**
	 * 
	 */
	private static final long serialVersionUID = -346637449460095584L;

	public StringWithoutSpaceDeserializer(Class<String> vc) {
        super(vc);
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    	String result = null;
    	if(p.getText() != null) {
    		result = p.getText().trim();
    		if(result.equals("")) {
    			result = null;
    		}	
    	}
        return result;
    }
}
