package team3_backend.backend.models;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import team3_backend.backend.models_reward.FitnessClass;
import team3_backend.backend.models_reward.Referral;
import team3_backend.backend.models_reward.RewardPoint;
import team3_backend.backend.models_reward.RewardRedemption;
import team3_backend.backend.models_reward.UnapprovedReward;

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
	
	@ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
        name="user_role_junction",
        joinColumns = {@JoinColumn(name="user_id")},
        inverseJoinColumns = {@JoinColumn(name="role_id")}
    )
    private Set<Role> authorities;
	

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "user_fitness_class",
		joinColumns = {@JoinColumn(name = "user_id")},
		inverseJoinColumns = {@JoinColumn(name = "class_id")}
	)
	private List<FitnessClass> fitnessClasses;
    
	@OneToMany(mappedBy = "referrer", cascade = CascadeType.REMOVE)
	private Set<Referral> referredUsers;

	@OneToMany(mappedBy = "referee", cascade = CascadeType.REMOVE)
	private Set<Referral> referringUsers;

	@OneToMany(mappedBy = "applicationUser", cascade = CascadeType.REMOVE)
	private Set<RewardPoint> rewardPoints;

	

@OneToMany(mappedBy = "applicationUser", cascade = CascadeType.REMOVE)
private Set<RewardRedemption> rewardRedemptions;

@OneToMany(mappedBy = "applicationUser", cascade = CascadeType.REMOVE)
private Set<UnapprovedReward> unapprovedRewards;

    public ApplicationUser() {
		super();
		authorities = new HashSet<>();
	}
	

	public ApplicationUser(Integer userId, String username, String password, Set<Role> authorities, String email, String registeredClass) {
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

    // Getters and setters for fitnessClasses
    public List<FitnessClass> getFitnessClasses() {
        return fitnessClasses;
    }

    public void setFitnessClasses(List<FitnessClass> fitnessClasses) {
        this.fitnessClasses = fitnessClasses;
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