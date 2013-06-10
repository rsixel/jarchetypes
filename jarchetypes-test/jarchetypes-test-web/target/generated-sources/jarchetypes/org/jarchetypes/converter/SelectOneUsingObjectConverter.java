package org.jarchetypes.converter;

import java.lang.reflect.Field;
import java.util.Collection;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
  
@FacesConverter("selectOneUsingObjectConverter") 
public class SelectOneUsingObjectConverter implements Converter {

	@SuppressWarnings("rawtypes")
	@Override  
    public Object getAsObject(FacesContext context,  
        UIComponent component, String value) {  
        if (value == null || value.equals(""))  
            return null;  
  
        try{  
          //  Long id = Long.valueOf(value);  
            Collection items =  (Collection) component.getAttributes().get("items");  
            return findById(items, value);  
        }catch(Exception ex){  
            throw new ConverterException("Não foi possível aplicar conversão de item com valor ["+value+"] no componente ["+component.getId()+"]", ex);  
        }  
    }  
      
    @Override  
    public String getAsString(FacesContext context, UIComponent component,  
            Object value) {  
        if (value == null || value.equals(""))  
            return "";  
          
        return getIdByReflection(value).toString();  
    }  
      
    @SuppressWarnings("rawtypes")
	private Object findById(Collection collection, String idToFind){  
        for (Object obj : collection){  
             String id = getIdByReflection(obj);  
             if (id.equals(idToFind))  
                 return obj;  
        }  
          
        return null;  
    }  
      
    private String getIdByReflection(Object bean){  
        try{  
        	Field idField = bean.getClass().getDeclaredField("id");  
           // Field idField = bean.getClass().getSuperclass().getDeclaredField("id");  
            idField.setAccessible(true);  
            return  String.valueOf(idField.get(bean));  
        }catch(Exception ex){  
            throw new RuntimeException("Não foi possível obter a propriedade 'id' do item",ex);  
        }  
    }  

}
