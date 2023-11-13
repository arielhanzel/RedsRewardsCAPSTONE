package team3_backend.backend.models;

import java.util.Collection;
import java.util.HashSet;
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
import jakarta.persistence.Table;

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
	private Integer points;
	private String registeredClasses;
	private Integer classesAttended;
	private Date checkinlist;
	private Date checkoutlist; 
	private String rewardRedemption;




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
	

	public ApplicationUser(Integer userId, String username, String password, Set<Role> authorities, String email, Integer points, Integer classesAttended, String registeredClasses, Date checkinlist, Date checkoutlist, String rewardRedemption) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.email = email;
		this.points = points;
		this.classesAttended = classesAttended;
		this.registeredClasses = registeredClasses;
		this.checkinlist = checkinlist;
		this.checkoutlist = checkoutlist;
		this.rewardRedemption = rewardRedemption;

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
	

	public Integer getPoints() {
		return this.points;
	}
	
	public void setPoints(Integer points) {
		this.points = points;
	}


	public Integer getclassesAttended() {
		return this.classesAttended;
	}
	
	public void setclassesAttended(Integer classesAttended) {
		this.classesAttended = classesAttended;
	}


	public String getregisteredClasses() {
	
		return this.password;
	}
	
	public void setregisteredClasses(String registeredClasses) {
		this.registeredClasses = registeredClasses;
	}


	public Date getcheckinlist() {
		return this.checkinlist;
	}


	public void setcheckinlist(Date checkinlist) {
		this.checkinlist = checkinlist;
	}

	public Date getcheckoutlist() {
		return this.checkoutlist;
	}


	public void setcheckoutlist(Date checkoutlist) {
		this.checkoutlist = checkoutlist;
	}


	public Date getrewardRedemption() {
		return this.rewardRedemption;
	}


	public void setrewardRedemption(Date rewardRedemption) {
		this.rewardRedemption = rewardRedemptiont;
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