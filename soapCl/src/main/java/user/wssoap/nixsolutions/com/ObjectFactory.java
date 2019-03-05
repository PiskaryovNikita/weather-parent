
package user.wssoap.nixsolutions.com;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the user.wssoap.nixsolutions.com package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _FindUserByEmail_QNAME = new QName("com.nixsolutions.wsSoap.user", "findUserByEmail");
    private final static QName _UpdateUserResponse_QNAME = new QName("com.nixsolutions.wsSoap.user", "updateUserResponse");
    private final static QName _User_QNAME = new QName("com.nixsolutions.wsSoap.user", "user");
    private final static QName _FindAllUsersResponse_QNAME = new QName("com.nixsolutions.wsSoap.user", "findAllUsersResponse");
    private final static QName _FindUserByLoginResponse_QNAME = new QName("com.nixsolutions.wsSoap.user", "findUserByLoginResponse");
    private final static QName _FindUserById_QNAME = new QName("com.nixsolutions.wsSoap.user", "findUserById");
    private final static QName _CreateUser_QNAME = new QName("com.nixsolutions.wsSoap.user", "createUser");
    private final static QName _FindAllUsers_QNAME = new QName("com.nixsolutions.wsSoap.user", "findAllUsers");
    private final static QName _FindUserByIdResponse_QNAME = new QName("com.nixsolutions.wsSoap.user", "findUserByIdResponse");
    private final static QName _RemoveUser_QNAME = new QName("com.nixsolutions.wsSoap.user", "removeUser");
    private final static QName _FindUserByEmailResponse_QNAME = new QName("com.nixsolutions.wsSoap.user", "findUserByEmailResponse");
    private final static QName _RemoveUserResponse_QNAME = new QName("com.nixsolutions.wsSoap.user", "removeUserResponse");
    private final static QName _FindUserByLogin_QNAME = new QName("com.nixsolutions.wsSoap.user", "findUserByLogin");
    private final static QName _UpdateUser_QNAME = new QName("com.nixsolutions.wsSoap.user", "updateUser");
    private final static QName _CreateUserResponse_QNAME = new QName("com.nixsolutions.wsSoap.user", "createUserResponse");
    private final static QName _Role_QNAME = new QName("com.nixsolutions.wsSoap.user", "role");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: user.wssoap.nixsolutions.com
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Role }
     * 
     */
    public Role createRole() {
        return new Role();
    }

    /**
     * Create an instance of {@link CreateUserResponse }
     * 
     */
    public CreateUserResponse createCreateUserResponse() {
        return new CreateUserResponse();
    }

    /**
     * Create an instance of {@link UpdateUser }
     * 
     */
    public UpdateUser createUpdateUser() {
        return new UpdateUser();
    }

    /**
     * Create an instance of {@link FindUserByLogin }
     * 
     */
    public FindUserByLogin createFindUserByLogin() {
        return new FindUserByLogin();
    }

    /**
     * Create an instance of {@link FindUserByEmailResponse }
     * 
     */
    public FindUserByEmailResponse createFindUserByEmailResponse() {
        return new FindUserByEmailResponse();
    }

    /**
     * Create an instance of {@link RemoveUserResponse }
     * 
     */
    public RemoveUserResponse createRemoveUserResponse() {
        return new RemoveUserResponse();
    }

    /**
     * Create an instance of {@link FindUserByIdResponse }
     * 
     */
    public FindUserByIdResponse createFindUserByIdResponse() {
        return new FindUserByIdResponse();
    }

    /**
     * Create an instance of {@link RemoveUser }
     * 
     */
    public RemoveUser createRemoveUser() {
        return new RemoveUser();
    }

    /**
     * Create an instance of {@link FindUserById }
     * 
     */
    public FindUserById createFindUserById() {
        return new FindUserById();
    }

    /**
     * Create an instance of {@link CreateUser }
     * 
     */
    public CreateUser createCreateUser() {
        return new CreateUser();
    }

    /**
     * Create an instance of {@link FindAllUsers }
     * 
     */
    public FindAllUsers createFindAllUsers() {
        return new FindAllUsers();
    }

    /**
     * Create an instance of {@link FindUserByEmail }
     * 
     */
    public FindUserByEmail createFindUserByEmail() {
        return new FindUserByEmail();
    }

    /**
     * Create an instance of {@link UpdateUserResponse }
     * 
     */
    public UpdateUserResponse createUpdateUserResponse() {
        return new UpdateUserResponse();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link FindAllUsersResponse }
     * 
     */
    public FindAllUsersResponse createFindAllUsersResponse() {
        return new FindAllUsersResponse();
    }

    /**
     * Create an instance of {@link FindUserByLoginResponse }
     * 
     */
    public FindUserByLoginResponse createFindUserByLoginResponse() {
        return new FindUserByLoginResponse();
    }

    /**
     * Create an instance of {@link Date }
     * 
     */
    public Date createDate() {
        return new Date();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindUserByEmail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "com.nixsolutions.wsSoap.user", name = "findUserByEmail")
    public JAXBElement<FindUserByEmail> createFindUserByEmail(FindUserByEmail value) {
        return new JAXBElement<FindUserByEmail>(_FindUserByEmail_QNAME, FindUserByEmail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "com.nixsolutions.wsSoap.user", name = "updateUserResponse")
    public JAXBElement<UpdateUserResponse> createUpdateUserResponse(UpdateUserResponse value) {
        return new JAXBElement<UpdateUserResponse>(_UpdateUserResponse_QNAME, UpdateUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link User }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "com.nixsolutions.wsSoap.user", name = "user")
    public JAXBElement<User> createUser(User value) {
        return new JAXBElement<User>(_User_QNAME, User.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllUsersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "com.nixsolutions.wsSoap.user", name = "findAllUsersResponse")
    public JAXBElement<FindAllUsersResponse> createFindAllUsersResponse(FindAllUsersResponse value) {
        return new JAXBElement<FindAllUsersResponse>(_FindAllUsersResponse_QNAME, FindAllUsersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindUserByLoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "com.nixsolutions.wsSoap.user", name = "findUserByLoginResponse")
    public JAXBElement<FindUserByLoginResponse> createFindUserByLoginResponse(FindUserByLoginResponse value) {
        return new JAXBElement<FindUserByLoginResponse>(_FindUserByLoginResponse_QNAME, FindUserByLoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindUserById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "com.nixsolutions.wsSoap.user", name = "findUserById")
    public JAXBElement<FindUserById> createFindUserById(FindUserById value) {
        return new JAXBElement<FindUserById>(_FindUserById_QNAME, FindUserById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "com.nixsolutions.wsSoap.user", name = "createUser")
    public JAXBElement<CreateUser> createCreateUser(CreateUser value) {
        return new JAXBElement<CreateUser>(_CreateUser_QNAME, CreateUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllUsers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "com.nixsolutions.wsSoap.user", name = "findAllUsers")
    public JAXBElement<FindAllUsers> createFindAllUsers(FindAllUsers value) {
        return new JAXBElement<FindAllUsers>(_FindAllUsers_QNAME, FindAllUsers.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindUserByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "com.nixsolutions.wsSoap.user", name = "findUserByIdResponse")
    public JAXBElement<FindUserByIdResponse> createFindUserByIdResponse(FindUserByIdResponse value) {
        return new JAXBElement<FindUserByIdResponse>(_FindUserByIdResponse_QNAME, FindUserByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "com.nixsolutions.wsSoap.user", name = "removeUser")
    public JAXBElement<RemoveUser> createRemoveUser(RemoveUser value) {
        return new JAXBElement<RemoveUser>(_RemoveUser_QNAME, RemoveUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindUserByEmailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "com.nixsolutions.wsSoap.user", name = "findUserByEmailResponse")
    public JAXBElement<FindUserByEmailResponse> createFindUserByEmailResponse(FindUserByEmailResponse value) {
        return new JAXBElement<FindUserByEmailResponse>(_FindUserByEmailResponse_QNAME, FindUserByEmailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "com.nixsolutions.wsSoap.user", name = "removeUserResponse")
    public JAXBElement<RemoveUserResponse> createRemoveUserResponse(RemoveUserResponse value) {
        return new JAXBElement<RemoveUserResponse>(_RemoveUserResponse_QNAME, RemoveUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindUserByLogin }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "com.nixsolutions.wsSoap.user", name = "findUserByLogin")
    public JAXBElement<FindUserByLogin> createFindUserByLogin(FindUserByLogin value) {
        return new JAXBElement<FindUserByLogin>(_FindUserByLogin_QNAME, FindUserByLogin.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "com.nixsolutions.wsSoap.user", name = "updateUser")
    public JAXBElement<UpdateUser> createUpdateUser(UpdateUser value) {
        return new JAXBElement<UpdateUser>(_UpdateUser_QNAME, UpdateUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "com.nixsolutions.wsSoap.user", name = "createUserResponse")
    public JAXBElement<CreateUserResponse> createCreateUserResponse(CreateUserResponse value) {
        return new JAXBElement<CreateUserResponse>(_CreateUserResponse_QNAME, CreateUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Role }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "com.nixsolutions.wsSoap.user", name = "role")
    public JAXBElement<Role> createRole(Role value) {
        return new JAXBElement<Role>(_Role_QNAME, Role.class, null, value);
    }

}
