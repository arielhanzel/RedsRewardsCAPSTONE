package team3_backend.backend.models;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import team3_backend.backend.models_reward.FitnessClass;

@Entity
@Table(name="users")
public class ApplicationUser implements UserDetails{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer userId;
	@Column(unique=true)
    private String username;
    private String password;  
	@Column(unique=true)
	private String email;
	
	@ManyToOne
    @JoinColumn(name = "classId", nullable = true)
    private FitnessClass fitnessClass;


	@ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
        name="user_role_junction",
        joinColumns = {@JoinColumn(name="user_id")},
        inverseJoinColumns = {@JoinColumn(name="role_id")}
    )
    private Set<Role> authorities;

    public ApplicationUser() {
		super();
		authorities = new HashSet<>();
	}
	

	public ApplicationUser(Integer userId, String username, String password, Set<Role> authorities, String email) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.email = email;
	}

    public Integer getUserId() {
		return this.userId;
	}
	
	public void setId(Integer userId) {
		this.userId = userId;
	}
	
	public void setAuthorities(Set<Role> authorities) {
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return this.authorities;
	}

	@Override
	public String getPassword() {
	
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	public FitnessClass getFitnessClass() {
		return fitnessClass;
	}


	public void setFitnessClass(FitnessClass fitnessClass) {
		this.fitnessClass = fitnessClass;
	}
	
	/* If you want account locking capabilities create variables and ways to set them for the methods below */
	@Override
	public boolean isAccountNonExpired() {
	
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
	
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}
    
}