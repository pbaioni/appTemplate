package app.persistence.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/** 
 * 	This is a data structure, so
 *  fields can be public. (Clean-Code)
 */
@Entity
@Table(name="dummyTable")
public class DummyEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    
    @Column
    public String name;
    @Column
    private int age;
    @Column
    private String citizenship;
	
	public DummyEntity() {
		//Default constructor needed for JPA.
	}
	
	public DummyEntity(String name, int age, String citizenship) {
		this.name = name;
		this.age = age;
		this.citizenship = citizenship;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCitizenship() {
		return citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	@Override
	public String toString() {
		return "DummyEntity [name=" + name + ", age=" + age + ", citizenship=" + citizenship + "]";
	}
}

