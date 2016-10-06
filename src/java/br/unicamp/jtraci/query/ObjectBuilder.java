package br.unicamp.jtraci.query;

import br.unicamp.jtraci.entities.Entity;

/**
 * Created by Du on 03/10/16.
 */
public class ObjectBuilder {




    public ObjectBuilder(){

    }


    public void convertToEntity(Class<?> entityType){

        if(entityType.isAssignableFrom(Entity.class)){

            entityType.getDeclaredFields();

        }
    }
}
