/*******************************************************************************
 * Copyright (c) 2016  DCA-FEEC-UNICAMP
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Contributors:
 * A. L. O. Paraense, E. M. Froes, R. R. Gudwin
 ******************************************************************************/

package br.unicamp.jtraci.query;

import br.unicamp.jtraci.entities.Entity;


public class ObjectBuilder {




    public ObjectBuilder(){

    }


    public void convertToEntity(Class<?> entityType){

        if(entityType.isAssignableFrom(Entity.class)){

            entityType.getDeclaredFields();

        }
    }
}
