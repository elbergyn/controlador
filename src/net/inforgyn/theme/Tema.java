package net.inforgyn.theme;

import net.inforgyn.impl.EntityPersistence;

public class Tema extends EntityPersistence{
    
	private static final long serialVersionUID = 1L;

	private String name;
     
    private String displayName;
     
    public Tema() {}
 
    public Tema(Long id, String displayName, String name) {
        this.id = id;
        this.displayName = displayName;
        this.name = name;
    }
 
    public Long getId() {
        return this.id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public String getDisplayName() {
        return displayName;
    }
 
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
     
    @Override
    public String toString() {
        return name;
    }
}
