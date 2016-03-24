/**
 * 
 */
package com.cosmosource.base.dao;

import java.util.Hashtable;

import javax.naming.Binding;
import javax.naming.CompoundName;
import javax.naming.Context;
import javax.naming.ContextNotEmptyException;
import javax.naming.Name;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameClassPair;
import javax.naming.NameNotFoundException;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.NotContextException;
import javax.naming.OperationNotSupportedException;
import javax.naming.directory.AttributeModificationException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InvalidAttributesException;
import javax.naming.directory.InvalidSearchControlsException;
import javax.naming.directory.InvalidSearchFilterException;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 * 
 */
public class BaseDaoLDAP {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	private LdapContext context;

	/**
	 * @param context
	 */
	public BaseDaoLDAP(LdapContext context) {

		this.context = context;
	}

	/**
	 * Creates a new instance of this context initialized using request
	 * controls.
	 * 
	 * This method is a convenience method for creating a new instance of this
	 * context for the purposes of multithreaded access. For example, if
	 * multiple threads want to use different context request controls, each
	 * thread may use this method to get its own copy of this context and
	 * set/get context request controls without having to synchronize with other
	 * threads.
	 * <p>
	 * The new context has the same environment properties and connection
	 * request controls as this context. See the class description for details.
	 * Implementations might also allow this context and the new context to
	 * share the same network connection or other resources if doing so does not
	 * impede the independence of either context.
	 * 
	 * @param requestControls
	 *            The possibly null request controls to use for the new context.
	 *            If null, the context is initialized with no request controls.
	 * 
	 * @return A non-null <tt>LdapContext</tt> instance.
	 * @exception NamingException
	 *                If an error occurred while creating the new instance.
	 * @see InitialLdapContext
	 */
	public LdapContext newInstance(Control[] requestControls)
			throws NamingException {
		return this.context.newInstance(requestControls);
	}

	/**
	 * Reconnects to the LDAP server using the supplied controls and this
	 * context's environment.
	 * <p>
	 * This method is a way to explicitly initiate an LDAP "bind" operation. For
	 * example, you can use this method to set request controls for the LDAP
	 * "bind" operation, or to explicitly connect to the server to get response
	 * controls returned by the LDAP "bind" operation.
	 * <p>
	 * This method sets this context's <tt>connCtls</tt> to be its new
	 * connection request controls. This context's context request controls are
	 * not affected. After this method has been invoked, any subsequent implicit
	 * reconnections will be done using <tt>connCtls</tt>. <tt>connCtls</tt> are
	 * also used as connection request controls for new context instances
	 * derived from this context. These connection request controls are not
	 * affected by <tt>setRequestControls()</tt>.
	 * <p>
	 * Service provider implementors should read the "Service Provider" section
	 * in the class description for implementation details.
	 * 
	 * @param connCtls
	 *            The possibly null controls to use. If null, no controls are
	 *            used.
	 * @exception NamingException
	 *                If an error occurred while reconnecting.
	 * @see #getConnectControls
	 * @see #newInstance
	 */
	public void reconnect(Control[] connCtls) throws NamingException {
		this.context.reconnect(connCtls);
	}

	/**
	 * Retrieves the connection request controls in effect for this context. The
	 * controls are owned by the JNDI implementation and are immutable. Neither
	 * the array nor the controls may be modified by the caller.
	 * 
	 * @return A possibly-null array of controls. null means no connect controls
	 *         have been set for this context.
	 * @exception NamingException
	 *                If an error occurred while getting the request controls.
	 */
	public Control[] getConnectControls() throws NamingException {
		return this.context.getConnectControls();
	}

	/**
	 * Sets the request controls for methods subsequently invoked on this
	 * context. The request controls are owned by the JNDI implementation and
	 * are immutable. Neither the array nor the controls may be modified by the
	 * caller.
	 * <p>
	 * This removes any previous request controls and adds
	 * <tt>requestControls</tt> for use by subsequent methods invoked on this
	 * context. This method does not affect this context's connection request
	 * controls.
	 * <p>
	 * Note that <tt>requestControls</tt> will be in effect until the next
	 * invocation of <tt>setRequestControls()</tt>. You need to explicitly
	 * invoke <tt>setRequestControls()</tt> with <tt>null</tt> or an empty array
	 * to clear the controls if you don't want them to affect the context
	 * methods any more. To check what request controls are in effect for this
	 * context, use <tt>getRequestControls()</tt>.
	 * 
	 * @param requestControls
	 *            The possibly null controls to use. If null, no controls are
	 *            used.
	 * @exception NamingException
	 *                If an error occurred while setting the request controls.
	 * @see #getRequestControls
	 */
	public void setRequestControls(Control[] requestControls)
			throws NamingException {
		this.context.setRequestControls(requestControls);
	}

	/**
	 * Retrieves the request controls in effect for this context. The request
	 * controls are owned by the JNDI implementation and are immutable. Neither
	 * the array nor the controls may be modified by the caller.
	 * 
	 * @return A possibly-null array of controls. null means no request controls
	 *         have been set for this context.
	 * @exception NamingException
	 *                If an error occurred while getting the request controls.
	 * @see #setRequestControls
	 */
	public Control[] getRequestControls() throws NamingException {
		return this.context.getRequestControls();
	}

	/**
	 * Retrieves the response controls produced as a result of the last method
	 * invoked on this context. The response controls are owned by the JNDI
	 * implementation and are immutable. Neither the array nor the controls may
	 * be modified by the caller.
	 * <p>
	 * These response controls might have been generated by a successful or
	 * failed operation.
	 * <p>
	 * When a context method that may return response controls is invoked,
	 * response controls from the previous method invocation are cleared.
	 * <tt>getResponseControls()</tt> returns all of the response controls
	 * generated by LDAP operations used by the context method in the order
	 * received from the LDAP server. Invoking <tt>getResponseControls()</tt>
	 * does not clear the response controls. You can call it many times (and get
	 * back the same controls) until the next context method that may return
	 * controls is invoked.
	 * <p>
	 * 
	 * @return A possibly null array of controls. If null, the previous method
	 *         invoked on this context did not produce any controls.
	 * @exception NamingException
	 *                If an error occurred while getting the response controls.
	 */
	public Control[] getResponseControls() throws NamingException {
		return this.context.getResponseControls();
	}

	/**
	 * Retrieves the named object. If <tt>name</tt> is empty, returns a new
	 * instance of this context (which represents the same naming context as
	 * this context, but its environment may be modified independently and it
	 * may be accessed concurrently).
	 * 
	 * @param name
	 *            the name of the object to look up
	 * @return the object bound to <tt>name</tt>
	 * @throws NamingException
	 *             if a naming exception is encountered
	 * 
	 * @see #lookup(String)
	 * @see #lookupLink(Name)
	 */
	public Object lookup(Name name) throws NamingException {
		return this.context.lookup(name);
	}

	/**
	 * Retrieves the named object. See {@link #lookup(Name)} for details.
	 * 
	 * @param name
	 *            the name of the object to look up
	 * @return the object bound to <tt>name</tt>
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public Object lookup(String name) throws NamingException {
		return this.context.lookup(name);
	}

	/**
	 * Binds a name to an object. All intermediate contexts and the target
	 * context (that named by all but terminal atomic component of the name)
	 * must already exist.
	 * 
	 * @param name
	 *            the name to bind; may not be empty
	 * @param obj
	 *            the object to bind; possibly null
	 * @throws NameAlreadyBoundException
	 *             if name is already bound
	 * @throws javax.naming.directory.InvalidAttributesException
	 *             if object did not supply all mandatory attributes
	 * @throws NamingException
	 *             if a naming exception is encountered
	 * 
	 * @see #bind(String, Object)
	 * @see #rebind(Name, Object)
	 * @see javax.naming.directory.DirContext#bind(Name, Object,
	 *      javax.naming.directory.Attributes)
	 */
	public void bind(Name name, Object obj) throws NamingException {
		this.context.bind(name, obj);
	}

	/**
	 * Binds a name to an object. See {@link #bind(Name, Object)} for details.
	 * 
	 * @param name
	 *            the name to bind; may not be empty
	 * @param obj
	 *            the object to bind; possibly null
	 * @throws NameAlreadyBoundException
	 *             if name is already bound
	 * @throws javax.naming.directory.InvalidAttributesException
	 *             if object did not supply all mandatory attributes
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public void bind(String name, Object obj) throws NamingException {
		this.context.bind(name, obj);
	}

	/**
	 * Binds a name to an object, along with associated attributes. If
	 * <tt>attrs</tt> is null, the resulting binding will have the attributes
	 * associated with <tt>obj</tt> if <tt>obj</tt> is a <tt>DirContext</tt>,
	 * and no attributes otherwise. If <tt>attrs</tt> is non-null, the resulting
	 * binding will have <tt>attrs</tt> as its attributes; any attributes
	 * associated with <tt>obj</tt> are ignored.
	 * 
	 * @param name
	 *            the name to bind; may not be empty
	 * @param obj
	 *            the object to bind; possibly null
	 * @param attrs
	 *            the attributes to associate with the binding
	 * 
	 * @throws NameAlreadyBoundException
	 *             if name is already bound
	 * @throws InvalidAttributesException
	 *             if some "mandatory" attributes of the binding are not
	 *             supplied
	 * @throws NamingException
	 *             if a naming exception is encountered
	 * 
	 * @see Context#bind(Name, Object)
	 * @see #rebind(Name, Object, Attributes)
	 */
	public void bind(Name name, Object obj, Attributes attrs)
			throws NamingException {
		this.context.bind(name, obj, attrs);
	}

	/**
	 * Binds a name to an object, along with associated attributes. See
	 * {@link #bind(Name, Object, Attributes)} for details.
	 * 
	 * @param name
	 *            the name to bind; may not be empty
	 * @param obj
	 *            the object to bind; possibly null
	 * @param attrs
	 *            the attributes to associate with the binding
	 * 
	 * @throws NameAlreadyBoundException
	 *             if name is already bound
	 * @throws InvalidAttributesException
	 *             if some "mandatory" attributes of the binding are not
	 *             supplied
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public void bind(String name, Object obj, Attributes attrs)
			throws NamingException {
		this.context.bind(name, obj, attrs);
	}

	/**
	 * Binds a name to an object, overwriting any existing binding. All
	 * intermediate contexts and the target context (that named by all but
	 * terminal atomic component of the name) must already exist.
	 * 
	 * <p>
	 * If the object is a <tt>DirContext</tt>, any existing attributes
	 * associated with the name are replaced with those of the object.
	 * Otherwise, any existing attributes associated with the name remain
	 * unchanged.
	 * 
	 * @param name
	 *            the name to bind; may not be empty
	 * @param obj
	 *            the object to bind; possibly null
	 * @throws javax.naming.directory.InvalidAttributesException
	 *             if object did not supply all mandatory attributes
	 * @throws NamingException
	 *             if a naming exception is encountered
	 * 
	 * @see #rebind(String, Object)
	 * @see #bind(Name, Object)
	 * @see javax.naming.directory.DirContext#rebind(Name, Object,
	 *      javax.naming.directory.Attributes)
	 * @see javax.naming.directory.DirContext
	 */
	public void rebind(Name name, Object obj) throws NamingException {
		this.context.rebind(name, obj);
	}

	/**
	 * Binds a name to an object, overwriting any existing binding. See
	 * {@link #rebind(Name, Object)} for details.
	 * 
	 * @param name
	 *            the name to bind; may not be empty
	 * @param obj
	 *            the object to bind; possibly null
	 * @throws javax.naming.directory.InvalidAttributesException
	 *             if object did not supply all mandatory attributes
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public void rebind(String name, Object obj) throws NamingException {
		this.context.rebind(name, obj);
	}

	/**
	 * Binds a name to an object, along with associated attributes, overwriting
	 * any existing binding. If <tt>attrs</tt> is null and <tt>obj</tt> is a
	 * <tt>DirContext</tt>, the attributes from <tt>obj</tt> are used. If
	 * <tt>attrs</tt> is null and <tt>obj</tt> is not a <tt>DirContext</tt>, any
	 * existing attributes associated with the object already bound in the
	 * directory remain unchanged. If <tt>attrs</tt> is non-null, any existing
	 * attributes associated with the object already bound in the directory are
	 * removed and <tt>attrs</tt> is associated with the named object. If
	 * <tt>obj</tt> is a <tt>DirContext</tt> and <tt>attrs</tt> is non-null, the
	 * attributes of <tt>obj</tt> are ignored.
	 * 
	 * @param name
	 *            the name to bind; may not be empty
	 * @param obj
	 *            the object to bind; possibly null
	 * @param attrs
	 *            the attributes to associate with the binding
	 * 
	 * @throws InvalidAttributesException
	 *             if some "mandatory" attributes of the binding are not
	 *             supplied
	 * @throws NamingException
	 *             if a naming exception is encountered
	 * 
	 * @see Context#bind(Name, Object)
	 * @see #bind(Name, Object, Attributes)
	 */
	public void rebind(Name name, Object obj, Attributes attrs)
			throws NamingException {
		this.context.rebind(name, obj, attrs);
	}

	/**
	 * Binds a name to an object, along with associated attributes, overwriting
	 * any existing binding. See {@link #rebind(Name, Object, Attributes)} for
	 * details.
	 * 
	 * @param name
	 *            the name to bind; may not be empty
	 * @param obj
	 *            the object to bind; possibly null
	 * @param attrs
	 *            the attributes to associate with the binding
	 * 
	 * @throws InvalidAttributesException
	 *             if some "mandatory" attributes of the binding are not
	 *             supplied
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public void rebind(String name, Object obj, Attributes attrs)
			throws NamingException {
		this.context.rebind(name, obj, attrs);
	}

	/**
	 * Unbinds the named object. Removes the terminal atomic name in
	 * <code>name</code> from the target context--that named by all but the
	 * terminal atomic part of <code>name</code>.
	 * 
	 * <p>
	 * This method is idempotent. It succeeds even if the terminal atomic name
	 * is not bound in the target context, but throws
	 * <tt>NameNotFoundException</tt> if any of the intermediate contexts do not
	 * exist.
	 * 
	 * <p>
	 * Any attributes associated with the name are removed. Intermediate
	 * contexts are not changed.
	 * 
	 * @param name
	 *            the name to unbind; may not be empty
	 * @throws NameNotFoundException
	 *             if an intermediate context does not exist
	 * @throws NamingException
	 *             if a naming exception is encountered
	 * @see #unbind(String)
	 */
	public void unbind(Name name) throws NamingException {
		this.context.unbind(name);
	}

	/**
	 * Unbinds the named object. See {@link #unbind(Name)} for details.
	 * 
	 * @param name
	 *            the name to unbind; may not be empty
	 * @throws NameNotFoundException
	 *             if an intermediate context does not exist
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public void unbind(String name) throws NamingException {
		this.context.unbind(name);
	}

	/**
	 * Binds a new name to the object bound to an old name, and unbinds the old
	 * name. Both names are relative to this context. Any attributes associated
	 * with the old name become associated with the new name. Intermediate
	 * contexts of the old name are not changed.
	 * 
	 * @param oldName
	 *            the name of the existing binding; may not be empty
	 * @param newName
	 *            the name of the new binding; may not be empty
	 * @throws NameAlreadyBoundException
	 *             if <tt>newName</tt> is already bound
	 * @throws NamingException
	 *             if a naming exception is encountered
	 * 
	 * @see #rename(String, String)
	 * @see #bind(Name, Object)
	 * @see #rebind(Name, Object)
	 */
	public void rename(Name oldName, Name newName) throws NamingException {
		this.context.rename(oldName, newName);
	}

	/**
	 * Binds a new name to the object bound to an old name, and unbinds the old
	 * name. See {@link #rename(Name, Name)} for details.
	 * 
	 * @param oldName
	 *            the name of the existing binding; may not be empty
	 * @param newName
	 *            the name of the new binding; may not be empty
	 * @throws NameAlreadyBoundException
	 *             if <tt>newName</tt> is already bound
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public void rename(String oldName, String newName) throws NamingException {
		this.context.rename(oldName, newName);
	}

	/**
	 * Enumerates the names bound in the named context, along with the class
	 * names of objects bound to them. The contents of any subcontexts are not
	 * included.
	 * 
	 * <p>
	 * If a binding is added to or removed from this context, its effect on an
	 * enumeration previously returned is undefined.
	 * 
	 * @param name
	 *            the name of the context to list
	 * @return an enumeration of the names and class names of the bindings in
	 *         this context. Each element of the enumeration is of type
	 *         <tt>NameClassPair</tt>.
	 * @throws NamingException
	 *             if a naming exception is encountered
	 * 
	 * @see #list(String)
	 * @see #listBindings(Name)
	 * @see NameClassPair
	 */
	public NamingEnumeration<NameClassPair> list(Name name)
			throws NamingException {
		return this.context.list(name);
	}

	/**
	 * Enumerates the names bound in the named context, along with the class
	 * names of objects bound to them. See {@link #list(Name)} for details.
	 * 
	 * @param name
	 *            the name of the context to list
	 * @return an enumeration of the names and class names of the bindings in
	 *         this context. Each element of the enumeration is of type
	 *         <tt>NameClassPair</tt>.
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public NamingEnumeration<NameClassPair> list(String name)
			throws NamingException {
		return this.context.list(name);
	}

	/**
	 * Enumerates the names bound in the named context, along with the objects
	 * bound to them. The contents of any subcontexts are not included.
	 * 
	 * <p>
	 * If a binding is added to or removed from this context, its effect on an
	 * enumeration previously returned is undefined.
	 * 
	 * @param name
	 *            the name of the context to list
	 * @return an enumeration of the bindings in this context. Each element of
	 *         the enumeration is of type <tt>Binding</tt>.
	 * @throws NamingException
	 *             if a naming exception is encountered
	 * 
	 * @see #listBindings(String)
	 * @see #list(Name)
	 * @see Binding
	 */
	public NamingEnumeration<Binding> listBindings(Name name)
			throws NamingException {
		return this.context.listBindings(name);
	}

	/**
	 * Enumerates the names bound in the named context, along with the objects
	 * bound to them. See {@link #listBindings(Name)} for details.
	 * 
	 * @param name
	 *            the name of the context to list
	 * @return an enumeration of the bindings in this context. Each element of
	 *         the enumeration is of type <tt>Binding</tt>.
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public NamingEnumeration<Binding> listBindings(String name)
			throws NamingException {
		return this.context.listBindings(name);
	}

	/**
	 * Destroys the named context and removes it from the namespace. Any
	 * attributes associated with the name are also removed. Intermediate
	 * contexts are not destroyed.
	 * 
	 * <p>
	 * This method is idempotent. It succeeds even if the terminal atomic name
	 * is not bound in the target context, but throws
	 * <tt>NameNotFoundException</tt> if any of the intermediate contexts do not
	 * exist.
	 * 
	 * <p>
	 * In a federated naming system, a context from one naming system may be
	 * bound to a name in another. One can subsequently look up and perform
	 * operations on the foreign context using a composite name. However, an
	 * attempt destroy the context using this composite name will fail with
	 * <tt>NotContextException</tt>, because the foreign context is not a
	 * "subcontext" of the context in which it is bound. Instead, use
	 * <tt>unbind()</tt> to remove the binding of the foreign context.
	 * Destroying the foreign context requires that the
	 * <tt>destroySubcontext()</tt> be performed on a context from the foreign
	 * context's "native" naming system.
	 * 
	 * @param name
	 *            the name of the context to be destroyed; may not be empty
	 * @throws NameNotFoundException
	 *             if an intermediate context does not exist
	 * @throws NotContextException
	 *             if the name is bound but does not name a context, or does not
	 *             name a context of the appropriate type
	 * @throws ContextNotEmptyException
	 *             if the named context is not empty
	 * @throws NamingException
	 *             if a naming exception is encountered
	 * 
	 * @see #destroySubcontext(String)
	 */
	public void destroySubcontext(Name name) throws NamingException {
		this.context.destroySubcontext(name);
	}

	/**
	 * Destroys the named context and removes it from the namespace. See
	 * {@link #destroySubcontext(Name)} for details.
	 * 
	 * @param name
	 *            the name of the context to be destroyed; may not be empty
	 * @throws NameNotFoundException
	 *             if an intermediate context does not exist
	 * @throws NotContextException
	 *             if the name is bound but does not name a context, or does not
	 *             name a context of the appropriate type
	 * @throws ContextNotEmptyException
	 *             if the named context is not empty
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public void destroySubcontext(String name) throws NamingException {
		this.context.destroySubcontext(name);
	}

	/**
	 * Creates and binds a new context. Creates a new context with the given
	 * name and binds it in the target context (that named by all but terminal
	 * atomic component of the name). All intermediate contexts and the target
	 * context must already exist.
	 * 
	 * @param name
	 *            the name of the context to create; may not be empty
	 * @return the newly created context
	 * 
	 * @throws NameAlreadyBoundException
	 *             if name is already bound
	 * @throws javax.naming.directory.InvalidAttributesException
	 *             if creation of the subcontext requires specification of
	 *             mandatory attributes
	 * @throws NamingException
	 *             if a naming exception is encountered
	 * 
	 * @see #createSubcontext(String)
	 * @see javax.naming.directory.DirContext#createSubcontext
	 */
	public Context createSubcontext(Name name) throws NamingException {
		return this.context.createSubcontext(name);
	}

	/**
	 * Creates and binds a new context. See {@link #createSubcontext(Name)} for
	 * details.
	 * 
	 * @param name
	 *            the name of the context to create; may not be empty
	 * @return the newly created context
	 * 
	 * @throws NameAlreadyBoundException
	 *             if name is already bound
	 * @throws javax.naming.directory.InvalidAttributesException
	 *             if creation of the subcontext requires specification of
	 *             mandatory attributes
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public Context createSubcontext(String name) throws NamingException {
		return this.context.createSubcontext(name);
	}

	/**
	 * Creates and binds a new context, along with associated attributes. This
	 * method creates a new subcontext with the given name, binds it in the
	 * target context (that named by all but terminal atomic component of the
	 * name), and associates the supplied attributes with the newly created
	 * object. All intermediate and target contexts must already exist. If
	 * <tt>attrs</tt> is null, this method is equivalent to
	 * <tt>Context.createSubcontext()</tt>.
	 * 
	 * @param name
	 *            the name of the context to create; may not be empty
	 * @param attrs
	 *            the attributes to associate with the newly created context
	 * @return the newly created context
	 * 
	 * @throws NameAlreadyBoundException
	 *             if the name is already bound
	 * @throws InvalidAttributesException
	 *             if <code>attrs</code> does not contain all the mandatory
	 *             attributes required for creation
	 * @throws NamingException
	 *             if a naming exception is encountered
	 * 
	 * @see Context#createSubcontext(Name)
	 */
	public DirContext createSubcontext(Name name, Attributes attrs)
			throws NamingException {
		return this.context.createSubcontext(name, attrs);
	}

	/**
	 * Creates and binds a new context, along with associated attributes. See
	 * {@link #createSubcontext(Name, Attributes)} for details.
	 * 
	 * @param name
	 *            the name of the context to create; may not be empty
	 * @param attrs
	 *            the attributes to associate with the newly created context
	 * @return the newly created context
	 * 
	 * @throws NameAlreadyBoundException
	 *             if the name is already bound
	 * @throws InvalidAttributesException
	 *             if <code>attrs</code> does not contain all the mandatory
	 *             attributes required for creation
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public DirContext createSubcontext(String name, Attributes attrs)
			throws NamingException {
		return this.context.createSubcontext(name, attrs);
	}

	/**
	 * Retrieves the named object, following links except for the terminal
	 * atomic component of the name. If the object bound to <tt>name</tt> is not
	 * a link, returns the object itself.
	 * 
	 * @param name
	 *            the name of the object to look up
	 * @return the object bound to <tt>name</tt>, not following the terminal
	 *         link (if any).
	 * @throws NamingException
	 *             if a naming exception is encountered
	 * 
	 * @see #lookupLink(String)
	 */
	public Object lookupLink(Name name) throws NamingException {
		return this.context.lookupLink(name);
	}

	/**
	 * Retrieves the named object, following links except for the terminal
	 * atomic component of the name. See {@link #lookupLink(Name)} for details.
	 * 
	 * @param name
	 *            the name of the object to look up
	 * @return the object bound to <tt>name</tt>, not following the terminal
	 *         link (if any)
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public Object lookupLink(String name) throws NamingException {
		return this.context.lookupLink(name);
	}

	/**
	 * Retrieves the parser associated with the named context. In a federation
	 * of namespaces, different naming systems will parse names differently.
	 * This method allows an application to get a parser for parsing names into
	 * their atomic components using the naming convention of a particular
	 * naming system. Within any single naming system, <tt>NameParser</tt>
	 * objects returned by this method must be equal (using the
	 * <tt>equals()</tt> test).
	 * 
	 * @param name
	 *            the name of the context from which to get the parser
	 * @return a name parser that can parse compound names into their atomic
	 *         components
	 * @throws NamingException
	 *             if a naming exception is encountered
	 * 
	 * @see #getNameParser(String)
	 * @see CompoundName
	 */
	public NameParser getNameParser(Name name) throws NamingException {
		return this.context.getNameParser(name);
	}

	/**
	 * Retrieves the parser associated with the named context. See
	 * {@link #getNameParser(Name)} for details.
	 * 
	 * @param name
	 *            the name of the context from which to get the parser
	 * @return a name parser that can parse compound names into their atomic
	 *         components
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public NameParser getNameParser(String name) throws NamingException {
		return this.context.getNameParser(name);
	}

	/**
	 * Composes the name of this context with a name relative to this context.
	 * Given a name (<code>name</code>) relative to this context, and the name (
	 * <code>prefix</code>) of this context relative to one of its ancestors,
	 * this method returns the composition of the two names using the syntax
	 * appropriate for the naming system(s) involved. That is, if
	 * <code>name</code> names an object relative to this context, the result is
	 * the name of the same object, but relative to the ancestor context. None
	 * of the names may be null.
	 * <p>
	 * For example, if this context is named "wiz.com" relative to the initial
	 * context, then
	 * 
	 * <pre>
	 * composeName(&quot;east&quot;, &quot;wiz.com&quot;)
	 * </pre>
	 * 
	 * might return <code>"east.wiz.com"</code>. If instead this context is
	 * named "org/research", then
	 * 
	 * <pre>
	 * composeName(&quot;user/jane&quot;, &quot;org/research&quot;)
	 * </pre>
	 * 
	 * might return <code>"org/research/user/jane"</code> while
	 * 
	 * <pre>
	 * composeName(&quot;user/jane&quot;, &quot;research&quot;)
	 * </pre>
	 * 
	 * returns <code>"research/user/jane"</code>.
	 * 
	 * @param name
	 *            a name relative to this context
	 * @param prefix
	 *            the name of this context relative to one of its ancestors
	 * @return the composition of <code>prefix</code> and <code>name</code>
	 * @throws NamingException
	 *             if a naming exception is encountered
	 * 
	 * @see #composeName(String, String)
	 */
	public Name composeName(Name name, Name prefix) throws NamingException {
		return this.context.composeName(name, prefix);
	}

	/**
	 * Composes the name of this context with a name relative to this context.
	 * See {@link #composeName(Name, Name)} for details.
	 * 
	 * @param name
	 *            a name relative to this context
	 * @param prefix
	 *            the name of this context relative to one of its ancestors
	 * @return the composition of <code>prefix</code> and <code>name</code>
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public String composeName(String name, String prefix)
			throws NamingException {
		return this.context.composeName(name, prefix);
	}

	/**
	 * Adds a new environment property to the environment of this context. If
	 * the property already exists, its value is overwritten. See class
	 * description for more details on environment properties.
	 * 
	 * @param propName
	 *            the name of the environment property to add; may not be null
	 * @param propVal
	 *            the value of the property to add; may not be null
	 * @return the previous value of the property, or null if the property was
	 *         not in the environment before
	 * @throws NamingException
	 *             if a naming exception is encountered
	 * 
	 * @see #getEnvironment()
	 * @see #removeFromEnvironment(String)
	 */
	public Object addToEnvironment(String propName, Object propVal)
			throws NamingException {
		return this.context.addToEnvironment(propName, propVal);
	}

	/**
	 * Removes an environment property from the environment of this context. See
	 * class description for more details on environment properties.
	 * 
	 * @param propName
	 *            the name of the environment property to remove; may not be
	 *            null
	 * @return the previous value of the property, or null if the property was
	 *         not in the environment
	 * @throws NamingException
	 *             if a naming exception is encountered
	 * 
	 * @see #getEnvironment()
	 * @see #addToEnvironment(String, Object)
	 */
	public Object removeFromEnvironment(String propName) throws NamingException {
		return this.context.removeFromEnvironment(propName);
	}

	/**
	 * Retrieves the environment in effect for this context. See class
	 * description for more details on environment properties.
	 * 
	 * <p>
	 * The caller should not make any changes to the object returned: their
	 * effect on the context is undefined. The environment of this context may
	 * be changed using <tt>addToEnvironment()</tt> and
	 * <tt>removeFromEnvironment()</tt>.
	 * 
	 * @return the environment of this context; never null
	 * @throws NamingException
	 *             if a naming exception is encountered
	 * 
	 * @see #addToEnvironment(String, Object)
	 * @see #removeFromEnvironment(String)
	 */
	public Hashtable<?, ?> getEnvironment() throws NamingException {
		return this.context.getEnvironment();
	}

	/**
	 * Closes this context. This method releases this context's resources
	 * immediately, instead of waiting for them to be released automatically by
	 * the garbage collector.
	 * 
	 * <p>
	 * This method is idempotent: invoking it on a context that has already been
	 * closed has no effect. Invoking any other method on a closed context is
	 * not allowed, and results in undefined behaviour.
	 * 
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public void close() throws NamingException {
		this.context.close();
	}

	/**
	 * Retrieves the full name of this context within its own namespace.
	 * 
	 * <p>
	 * Many naming services have a notion of a "full name" for objects in their
	 * respective namespaces. For example, an LDAP entry has a distinguished
	 * name, and a DNS record has a fully qualified name. This method allows the
	 * client application to retrieve this name. The string returned by this
	 * method is not a JNDI composite name and should not be passed directly to
	 * context methods. In naming systems for which the notion of full name does
	 * not make sense, <tt>OperationNotSupportedException</tt> is thrown.
	 * 
	 * @return this context's name in its own namespace; never null
	 * @throws OperationNotSupportedException
	 *             if the naming system does not have the notion of a full name
	 * @throws NamingException
	 *             if a naming exception is encountered
	 * 
	 * @since 1.3
	 */
	public String getNameInNamespace() throws NamingException {
		return this.context.getNameInNamespace();
	}

	// -------------------- attributes operations

	/**
	 * Retrieves all of the attributes associated with a named object. See the
	 * class description regarding attribute models, attribute type names, and
	 * operational attributes.
	 * 
	 * @param name
	 *            the name of the object from which to retrieve attributes
	 * @return the set of attributes associated with <code>name</code>. Returns
	 *         an empty attribute set if name has no attributes; never null.
	 * @throws NamingException
	 *             if a naming exception is encountered
	 * 
	 * @see #getAttributes(String)
	 * @see #getAttributes(Name, String[])
	 */
	public Attributes getAttributes(Name name) throws NamingException {
		return this.context.getAttributes(name);
	}

	/**
	 * Retrieves all of the attributes associated with a named object. See
	 * {@link #getAttributes(Name)} for details.
	 * 
	 * @param name
	 *            the name of the object from which to retrieve attributes
	 * @return the set of attributes associated with <code>name</code>
	 * 
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public Attributes getAttributes(String name) throws NamingException {
		return this.context.getAttributes(name);
	}

	/**
	 * Retrieves selected attributes associated with a named object. See the
	 * class description regarding attribute models, attribute type names, and
	 * operational attributes.
	 * 
	 * <p>
	 * If the object does not have an attribute specified, the directory will
	 * ignore the nonexistent attribute and return those requested attributes
	 * that the object does have.
	 * 
	 * <p>
	 * A directory might return more attributes than was requested (see
	 * <strong>Attribute Type Names</strong> in the class description), but is
	 * not allowed to return arbitrary, unrelated attributes.
	 * 
	 * <p>
	 * See also <strong>Operational Attributes</strong> in the class
	 * description.
	 * 
	 * @param name
	 *            the name of the object from which to retrieve attributes
	 * @param attrIds
	 *            the identifiers of the attributes to retrieve. null indicates
	 *            that all attributes should be retrieved; an empty array
	 *            indicates that none should be retrieved.
	 * @return the requested attributes; never null
	 * 
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public Attributes getAttributes(Name name, String[] attrIds)
			throws NamingException {
		return this.context.getAttributes(name, attrIds);
	}

	/**
	 * Retrieves selected attributes associated with a named object. See
	 * {@link #getAttributes(Name, String[])} for details.
	 * 
	 * @param name
	 *            The name of the object from which to retrieve attributes
	 * @param attrIds
	 *            the identifiers of the attributes to retrieve. null indicates
	 *            that all attributes should be retrieved; an empty array
	 *            indicates that none should be retrieved.
	 * @return the requested attributes; never null
	 * 
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public Attributes getAttributes(String name, String[] attrIds)
			throws NamingException {
		return this.context.getAttributes(name, attrIds);
	}

	/**
	 * Modifies the attributes associated with a named object. The order of the
	 * modifications is not specified. Where possible, the modifications are
	 * performed atomically.
	 * 
	 * @param name
	 *            the name of the object whose attributes will be updated
	 * @param mod_op
	 *            the modification operation, one of: <code>ADD_ATTRIBUTE</code>
	 *            , <code>REPLACE_ATTRIBUTE</code>,
	 *            <code>REMOVE_ATTRIBUTE</code>.
	 * @param attrs
	 *            the attributes to be used for the modification; may not be
	 *            null
	 * 
	 * @throws AttributeModificationException
	 *             if the modification cannot be completed successfully
	 * @throws NamingException
	 *             if a naming exception is encountered
	 * 
	 * @see #modifyAttributes(Name, ModificationItem[])
	 */
	public void modifyAttributes(Name name, int mod_op, Attributes attrs)
			throws NamingException {
		this.context.modifyAttributes(name, mod_op, attrs);
	}

	/**
	 * Modifies the attributes associated with a named object. See
	 * {@link #modifyAttributes(Name, int, Attributes)} for details.
	 * 
	 * @param name
	 *            the name of the object whose attributes will be updated
	 * @param mod_op
	 *            the modification operation, one of: <code>ADD_ATTRIBUTE</code>
	 *            , <code>REPLACE_ATTRIBUTE</code>,
	 *            <code>REMOVE_ATTRIBUTE</code>.
	 * @param attrs
	 *            the attributes to be used for the modification; may not be
	 *            null
	 * 
	 * @throws AttributeModificationException
	 *             if the modification cannot be completed successfully
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public void modifyAttributes(String name, int mod_op, Attributes attrs)
			throws NamingException {
		this.context.modifyAttributes(name, mod_op, attrs);
	}

	/**
	 * Modifies the attributes associated with a named object using an ordered
	 * list of modifications. The modifications are performed in the order
	 * specified. Each modification specifies a modification operation code and
	 * an attribute on which to operate. Where possible, the modifications are
	 * performed atomically.
	 * 
	 * @param name
	 *            the name of the object whose attributes will be updated
	 * @param mods
	 *            an ordered sequence of modifications to be performed; may not
	 *            be null
	 * 
	 * @throws AttributeModificationException
	 *             if the modifications cannot be completed successfully
	 * @throws NamingException
	 *             if a naming exception is encountered
	 * 
	 * @see #modifyAttributes(Name, int, Attributes)
	 * @see ModificationItem
	 */
	public void modifyAttributes(Name name, ModificationItem[] mods)
			throws NamingException {
		this.context.modifyAttributes(name, mods);
	}

	/**
	 * Modifies the attributes associated with a named object using an ordered
	 * list of modifications. See
	 * {@link #modifyAttributes(Name, ModificationItem[])} for details.
	 * 
	 * @param name
	 *            the name of the object whose attributes will be updated
	 * @param mods
	 *            an ordered sequence of modifications to be performed; may not
	 *            be null
	 * 
	 * @throws AttributeModificationException
	 *             if the modifications cannot be completed successfully
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public void modifyAttributes(String name, ModificationItem[] mods)
			throws NamingException {
		this.context.modifyAttributes(name, mods);
	}

	// -------------------- schema operations

	/**
	 * Retrieves the schema associated with the named object. The schema
	 * describes rules regarding the structure of the namespace and the
	 * attributes stored within it. The schema specifies what types of objects
	 * can be added to the directory and where they can be added; what mandatory
	 * and optional attributes an object can have. The range of support for
	 * schemas is directory-specific.
	 * 
	 * <p>
	 * This method returns the root of the schema information tree that is
	 * applicable to the named object. Several named objects (or even an entire
	 * directory) might share the same schema.
	 * 
	 * <p>
	 * Issues such as structure and contents of the schema tree, permission to
	 * modify to the contents of the schema tree, and the effect of such
	 * modifications on the directory are dependent on the underlying directory.
	 * 
	 * @param name
	 *            the name of the object whose schema is to be retrieved
	 * @return the schema associated with the context; never null
	 * @throws OperationNotSupportedException
	 *             if schema not supported
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public DirContext getSchema(Name name) throws NamingException {
		return this.context.getSchema(name);
	}

	/**
	 * Retrieves the schema associated with the named object. See
	 * {@link #getSchema(Name)} for details.
	 * 
	 * @param name
	 *            the name of the object whose schema is to be retrieved
	 * @return the schema associated with the context; never null
	 * @throws OperationNotSupportedException
	 *             if schema not supported
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public DirContext getSchema(String name) throws NamingException {
		return this.context.getSchema(name);
	}

	/**
	 * Retrieves a context containing the schema objects of the named object's
	 * class definitions.
	 * <p>
	 * One category of information found in directory schemas is
	 * <em>class definitions</em>. An "object class" definition specifies the
	 * object's <em>type</em> and what attributes (mandatory and optional) the
	 * object must/can have. Note that the term "object class" being referred to
	 * here is in the directory sense rather than in the Java sense. For
	 * example, if the named object is a directory object of "Person" class,
	 * <tt>getSchemaClassDefinition()</tt> would return a <tt>DirContext</tt>
	 * representing the (directory's) object class definition of "Person".
	 * <p>
	 * The information that can be retrieved from an object class definition is
	 * directory-dependent.
	 * <p>
	 * Prior to JNDI 1.2, this method returned a single schema object
	 * representing the class definition of the named object. Since JNDI 1.2,
	 * this method returns a <tt>DirContext</tt> containing all of the named
	 * object's class definitions.
	 * 
	 * @param name
	 *            the name of the object whose object class definition is to be
	 *            retrieved
	 * @return the <tt>DirContext</tt> containing the named object's class
	 *         definitions; never null
	 * 
	 * @throws OperationNotSupportedException
	 *             if schema not supported
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public DirContext getSchemaClassDefinition(Name name)
			throws NamingException {
		return this.context.getSchemaClassDefinition(name);
	}

	/**
	 * Retrieves a context containing the schema objects of the named object's
	 * class definitions. See {@link #getSchemaClassDefinition(Name)} for
	 * details.
	 * 
	 * @param name
	 *            the name of the object whose object class definition is to be
	 *            retrieved
	 * @return the <tt>DirContext</tt> containing the named object's class
	 *         definitions; never null
	 * 
	 * @throws OperationNotSupportedException
	 *             if schema not supported
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public DirContext getSchemaClassDefinition(String name)
			throws NamingException {
		return this.context.getSchemaClassDefinition(name);
	}

	// -------------------- search operations

	/**
	 * Searches in a single context for objects that contain a specified set of
	 * attributes, and retrieves selected attributes. The search is performed
	 * using the default <code>SearchControls</code> settings.
	 * <p>
	 * For an object to be selected, each attribute in
	 * <code>matchingAttributes</code> must match some attribute of the object.
	 * If <code>matchingAttributes</code> is empty or null, all objects in the
	 * target context are returned.
	 * <p>
	 * An attribute <em>A</em><sub>1</sub> in <code>matchingAttributes</code> is
	 * considered to match an attribute <em>A</em><sub>2</sub> of an object if
	 * <em>A</em><sub>1</sub> and <em>A</em><sub>2</sub> have the same
	 * identifier, and each value of <em>A</em><sub>1</sub> is equal to some
	 * value of <em>A</em><sub>2</sub>. This implies that the order of values is
	 * not significant, and that <em>A</em><sub>2</sub> may contain "extra"
	 * values not found in <em>A</em><sub>1</sub> without affecting the
	 * comparison. It also implies that if <em>A</em><sub>1</sub> has no values,
	 * then testing for a match is equivalent to testing for the presence of an
	 * attribute <em>A</em><sub>2</sub> with the same identifier.
	 * <p>
	 * The precise definition of "equality" used in comparing attribute values
	 * is defined by the underlying directory service. It might use the
	 * <code>Object.equals</code> method, for example, or might use a schema to
	 * specify a different equality operation. For matching based on operations
	 * other than equality (such as substring comparison) use the version of the
	 * <code>search</code> method that takes a filter argument.
	 * <p>
	 * When changes are made to this <tt>DirContext</tt>, the effect on
	 * enumerations returned by prior calls to this method is undefined.
	 * <p>
	 * If the object does not have the attribute specified, the directory will
	 * ignore the nonexistent attribute and return the requested attributes that
	 * the object does have.
	 * <p>
	 * A directory might return more attributes than was requested (see
	 * <strong>Attribute Type Names</strong> in the class description), but is
	 * not allowed to return arbitrary, unrelated attributes.
	 * <p>
	 * See also <strong>Operational Attributes</strong> in the class
	 * description.
	 * 
	 * @param name
	 *            the name of the context to search
	 * @param matchingAttributes
	 *            the attributes to search for. If empty or null, all objects in
	 *            the target context are returned.
	 * @param attributesToReturn
	 *            the attributes to return. null indicates that all attributes
	 *            are to be returned; an empty array indicates that none are to
	 *            be returned.
	 * @return a non-null enumeration of <tt>SearchResult</tt> objects. Each
	 *         <tt>SearchResult</tt> contains the attributes identified by
	 *         <code>attributesToReturn</code> and the name of the corresponding
	 *         object, named relative to the context named by <code>name</code>.
	 * @throws NamingException
	 *             if a naming exception is encountered
	 * 
	 * @see SearchControls
	 * @see SearchResult
	 * @see #search(Name, String, Object[], SearchControls)
	 */
	public NamingEnumeration<SearchResult> search(Name name,
			Attributes matchingAttributes, String[] attributesToReturn)
			throws NamingException {
		return this.context.search(name, matchingAttributes);
	}

	/**
	 * Searches in a single context for objects that contain a specified set of
	 * attributes, and retrieves selected attributes. See
	 * {@link #search(Name, Attributes, String[])} for details.
	 * 
	 * @param name
	 *            the name of the context to search
	 * @param matchingAttributes
	 *            the attributes to search for
	 * @param attributesToReturn
	 *            the attributes to return
	 * @return a non-null enumeration of <tt>SearchResult</tt> objects
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public NamingEnumeration<SearchResult> search(String name,
			Attributes matchingAttributes, String[] attributesToReturn)
			throws NamingException {
		return this.context.search(name, matchingAttributes);
	}

	/**
	 * Searches in a single context for objects that contain a specified set of
	 * attributes. This method returns all the attributes of such objects. It is
	 * equivalent to supplying null as the <tt>atributesToReturn</tt> parameter
	 * to the method <code>search(Name, Attributes, String[])</code>. <br>
	 * See {@link #search(Name, Attributes, String[])} for a full description.
	 * 
	 * @param name
	 *            the name of the context to search
	 * @param matchingAttributes
	 *            the attributes to search for
	 * @return an enumeration of <tt>SearchResult</tt> objects
	 * @throws NamingException
	 *             if a naming exception is encountered
	 * 
	 * @see #search(Name, Attributes, String[])
	 */
	public NamingEnumeration<SearchResult> search(Name name,
			Attributes matchingAttributes) throws NamingException {
		return this.context.search(name, matchingAttributes);
	}

	/**
	 * Searches in a single context for objects that contain a specified set of
	 * attributes. See {@link #search(Name, Attributes)} for details.
	 * 
	 * @param name
	 *            the name of the context to search
	 * @param matchingAttributes
	 *            the attributes to search for
	 * @return an enumeration of <tt>SearchResult</tt> objects
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public NamingEnumeration<SearchResult> search(String name,
			Attributes matchingAttributes) throws NamingException {
		return this.context.search(name, matchingAttributes);
	}

	/**
	 * Searches in the named context or object for entries that satisfy the
	 * given search filter. Performs the search as specified by the search
	 * controls.
	 * <p>
	 * The format and interpretation of <code>filter</code> follows RFC 2254
	 * with the following interpretations for <code>attr</code> and
	 * <code>value</code> mentioned in the RFC.
	 * <p>
	 * <code>attr</code> is the attribute's identifier.
	 * <p>
	 * <code>value</code> is the string representation the attribute's value.
	 * The translation of this string representation into the attribute's value
	 * is directory-specific.
	 * <p>
	 * For the assertion "someCount=127", for example, <code>attr</code> is
	 * "someCount" and <code>value</code> is "127". The provider determines,
	 * based on the attribute ID ("someCount") (and possibly its schema), that
	 * the attribute's value is an integer. It then parses the string "127"
	 * appropriately.
	 * <p>
	 * Any non-ASCII characters in the filter string should be represented by
	 * the appropriate Java (Unicode) characters, and not encoded as UTF-8
	 * octets. Alternately, the "backslash-hexcode" notation described in RFC
	 * 2254 may be used.
	 * <p>
	 * If the directory does not support a string representation of some or all
	 * of its attributes, the form of <code>search</code> that accepts filter
	 * arguments in the form of Objects can be used instead. The service
	 * provider for such a directory would then translate the filter arguments
	 * to its service-specific representation for filter evaluation. See
	 * <code>search(Name, String, Object[], SearchControls)</code>.
	 * <p>
	 * RFC 2254 defines certain operators for the filter, including substring
	 * matches, equality, approximate match, greater than, less than. These
	 * operators are mapped to operators with corresponding semantics in the
	 * underlying directory. For example, for the equals operator, suppose the
	 * directory has a matching rule defining "equality" of the attributes in
	 * the filter. This rule would be used for checking equality of the
	 * attributes specified in the filter with the attributes of objects in the
	 * directory. Similarly, if the directory has a matching rule for ordering,
	 * this rule would be used for making "greater than" and "less than"
	 * comparisons.
	 * <p>
	 * Not all of the operators defined in RFC 2254 are applicable to all
	 * attributes. When an operator is not applicable, the exception
	 * <code>InvalidSearchFilterException</code> is thrown.
	 * <p>
	 * The result is returned in an enumeration of <tt>SearchResult</tt>s. Each
	 * <tt>SearchResult</tt> contains the name of the object and other
	 * information about the object (see SearchResult). The name is either
	 * relative to the target context of the search (which is named by the
	 * <code>name</code> parameter), or it is a URL string. If the target
	 * context is included in the enumeration (as is possible when
	 * <code>cons</code> specifies a search scope of
	 * <code>SearchControls.OBJECT_SCOPE</code> or
	 * <code>SearchControls.SUBSTREE_SCOPE</code>), its name is the empty
	 * string. The <tt>SearchResult</tt> may also contain attributes of the
	 * matching object if the <tt>cons</tt> argument specified that attributes
	 * be returned.
	 * <p>
	 * If the object does not have a requested attribute, that nonexistent
	 * attribute will be ignored. Those requested attributes that the object
	 * does have will be returned.
	 * <p>
	 * A directory might return more attributes than were requested (see
	 * <strong>Attribute Type Names</strong> in the class description) but is
	 * not allowed to return arbitrary, unrelated attributes.
	 * <p>
	 * See also <strong>Operational Attributes</strong> in the class
	 * description.
	 * 
	 * @param name
	 *            the name of the context or object to search
	 * @param filter
	 *            the filter expression to use for the search; may not be null
	 * @param cons
	 *            the search controls that control the search. If null, the
	 *            default search controls are used (equivalent to
	 *            <tt>(new SearchControls())</tt>).
	 * @return an enumeration of <tt>SearchResult</tt>s of the objects that
	 *         satisfy the filter; never null
	 * 
	 * @throws InvalidSearchFilterException
	 *             if the search filter specified is not supported or understood
	 *             by the underlying directory
	 * @throws InvalidSearchControlsException
	 *             if the search controls contain invalid settings
	 * @throws NamingException
	 *             if a naming exception is encountered
	 * 
	 * @see #search(Name, String, Object[], SearchControls)
	 * @see SearchControls
	 * @see SearchResult
	 */
	public NamingEnumeration<SearchResult> search(Name name, String filter,
			SearchControls cons) throws NamingException {
		return this.context.search(name, filter, cons);
	}

	/**
	 * Searches in the named context or object for entries that satisfy the
	 * given search filter. Performs the search as specified by the search
	 * controls. See {@link #search(Name, String, SearchControls)} for details.
	 * 
	 * @param name
	 *            the name of the context or object to search
	 * @param filter
	 *            the filter expression to use for the search; may not be null
	 * @param cons
	 *            the search controls that control the search. If null, the
	 *            default search controls are used (equivalent to
	 *            <tt>(new SearchControls())</tt>).
	 * 
	 * @return an enumeration of <tt>SearchResult</tt>s for the objects that
	 *         satisfy the filter.
	 * @throws InvalidSearchFilterException
	 *             if the search filter specified is not supported or understood
	 *             by the underlying directory
	 * @throws InvalidSearchControlsException
	 *             if the search controls contain invalid settings
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public NamingEnumeration<SearchResult> search(String name, String filter,
			SearchControls cons) throws NamingException {
		return this.context.search(name, filter, cons);
	}

	/**
	 * Searches in the named context or object for entries that satisfy the
	 * given search filter. Performs the search as specified by the search
	 * controls.
	 * <p>
	 * The interpretation of <code>filterExpr</code> is based on RFC 2254. It
	 * may additionally contain variables of the form <code>{i}</code> -- where
	 * <code>i</code> is an integer -- that refer to objects in the
	 * <code>filterArgs</code> array. The interpretation of
	 * <code>filterExpr</code> is otherwise identical to that of the
	 * <code>filter</code> parameter of the method
	 * <code>search(Name, String, SearchControls)</code>.
	 * <p>
	 * When a variable <code>{i}</code> appears in a search filter, it indicates
	 * that the filter argument <code>filterArgs[i]</code> is to be used in that
	 * place. Such variables may be used wherever an <em>attr</em>,
	 * <em>value</em>, or <em>matchingrule</em> production appears in the filter
	 * grammar of RFC 2254, section 4. When a string-valued filter argument is
	 * substituted for a variable, the filter is interpreted as if the string
	 * were given in place of the variable, with any characters having special
	 * significance within filters (such as <code>'*'</code>) having been
	 * escaped according to the rules of RFC 2254.
	 * <p>
	 * For directories that do not use a string representation for some or all
	 * of their attributes, the filter argument corresponding to an attribute
	 * value may be of a type other than String. Directories that support
	 * unstructured binary-valued attributes, for example, should accept byte
	 * arrays as filter arguments. The interpretation (if any) of filter
	 * arguments of any other type is determined by the service provider for
	 * that directory, which maps the filter operations onto operations with
	 * corresponding semantics in the underlying directory.
	 * <p>
	 * This method returns an enumeration of the results. Each element in the
	 * enumeration contains the name of the object and other information about
	 * the object (see <code>SearchResult</code>). The name is either relative
	 * to the target context of the search (which is named by the
	 * <code>name</code> parameter), or it is a URL string. If the target
	 * context is included in the enumeration (as is possible when
	 * <code>cons</code> specifies a search scope of
	 * <code>SearchControls.OBJECT_SCOPE</code> or
	 * <code>SearchControls.SUBSTREE_SCOPE</code>), its name is the empty
	 * string.
	 * <p>
	 * The <tt>SearchResult</tt> may also contain attributes of the matching
	 * object if the <tt>cons</tt> argument specifies that attributes be
	 * returned.
	 * <p>
	 * If the object does not have a requested attribute, that nonexistent
	 * attribute will be ignored. Those requested attributes that the object
	 * does have will be returned.
	 * <p>
	 * A directory might return more attributes than were requested (see
	 * <strong>Attribute Type Names</strong> in the class description) but is
	 * not allowed to return arbitrary, unrelated attributes.
	 * <p>
	 * If a search filter with invalid variable substitutions is provided to
	 * this method, the result is undefined. When changes are made to this
	 * DirContext, the effect on enumerations returned by prior calls to this
	 * method is undefined.
	 * <p>
	 * See also <strong>Operational Attributes</strong> in the class
	 * description.
	 * 
	 * @param name
	 *            the name of the context or object to search
	 * @param filterExpr
	 *            the filter expression to use for the search. The expression
	 *            may contain variables of the form "<code>{i}</code>" where
	 *            <code>i</code> is a nonnegative integer. May not be null.
	 * @param filterArgs
	 *            the array of arguments to substitute for the variables in
	 *            <code>filterExpr</code>. The value of
	 *            <code>filterArgs[i]</code> will replace each occurrence of "
	 *            <code>{i}</code>". If null, equivalent to an empty array.
	 * @param cons
	 *            the search controls that control the search. If null, the
	 *            default search controls are used (equivalent to
	 *            <tt>(new SearchControls())</tt>).
	 * @return an enumeration of <tt>SearchResult</tt>s of the objects that
	 *         satisfy the filter; never null
	 * 
	 * @throws ArrayIndexOutOfBoundsException
	 *             if <tt>filterExpr</tt> contains <code>{i}</code> expressions
	 *             where <code>i</code> is outside the bounds of the array
	 *             <code>filterArgs</code>
	 * @throws InvalidSearchControlsException
	 *             if <tt>cons</tt> contains invalid settings
	 * @throws InvalidSearchFilterException
	 *             if <tt>filterExpr</tt> with <tt>filterArgs</tt> represents an
	 *             invalid search filter
	 * @throws NamingException
	 *             if a naming exception is encountered
	 * 
	 * @see #search(Name, Attributes, String[])
	 * @see java.text.MessageFormat
	 */
	public NamingEnumeration<SearchResult> search(Name name, String filterExpr,
			Object[] filterArgs, SearchControls cons) throws NamingException {
		return this.context.search(name, filterExpr, filterArgs, cons);
	}

	/**
	 * Searches in the named context or object for entries that satisfy the
	 * given search filter. Performs the search as specified by the search
	 * controls. See {@link #search(Name, String, Object[], SearchControls)} for
	 * details.
	 * 
	 * @param name
	 *            the name of the context or object to search
	 * @param filterExpr
	 *            the filter expression to use for the search. The expression
	 *            may contain variables of the form "<code>{i}</code>" where
	 *            <code>i</code> is a nonnegative integer. May not be null.
	 * @param filterArgs
	 *            the array of arguments to substitute for the variables in
	 *            <code>filterExpr</code>. The value of
	 *            <code>filterArgs[i]</code> will replace each occurrence of "
	 *            <code>{i}</code>". If null, equivalent to an empty array.
	 * @param cons
	 *            the search controls that control the search. If null, the
	 *            default search controls are used (equivalent to
	 *            <tt>(new SearchControls())</tt>).
	 * @return an enumeration of <tt>SearchResult</tt>s of the objects that
	 *         satisfy the filter; never null
	 * 
	 * @throws ArrayIndexOutOfBoundsException
	 *             if <tt>filterExpr</tt> contains <code>{i}</code> expressions
	 *             where <code>i</code> is outside the bounds of the array
	 *             <code>filterArgs</code>
	 * @throws InvalidSearchControlsException
	 *             if <tt>cons</tt> contains invalid settings
	 * @throws InvalidSearchFilterException
	 *             if <tt>filterExpr</tt> with <tt>filterArgs</tt> represents an
	 *             invalid search filter
	 * @throws NamingException
	 *             if a naming exception is encountered
	 */
	public NamingEnumeration<SearchResult> search(String name,
			String filterExpr, Object[] filterArgs, SearchControls cons)
			throws NamingException {
		return this.context.search(name, filterExpr, filterArgs, cons);
	}

	/**
	 * @return the context
	 */
	public LdapContext getContext() {
		return context;
	}

	/**
	 * @param context
	 *            the context to set
	 */
	public void setContext(LdapContext context) {
		this.context = context;
	}

}
