/*
 * Generated by XDoclet - Do not edit!
 */
package se.anatom.ejbca.ra;

/**
 * Remote interface for UserAdminSession.
 */
public interface IUserAdminSessionRemote
   extends javax.ejb.EJBObject,UserAdminConstants
{
   /**
    * Implements IUserAdminSession::addUser. Implements a mechanism that uses UserDataEntity Bean.
    * @param admin the administrator pwrforming the action
    * @param username the unique username.
    * @param password the password used for authentication.
    * @param subjectdn the DN the subject is given in his certificate.
    * @param subjectaltname the Subject Alternative Name to be used.
    * @param email the email of the subject or null.
    * @param clearpwd true if the password will be stored in clear form in the db, otherwise it is hashed.
    * @param endentityprofileid the id number of the end entity profile bound to this user.
    * @param certificateprofileid the id number of the certificate profile that should be generated for the user.
    * @param type of user i.e administrator, keyrecoverable and/or sendnotification
    * @param tokentype the type of token to be generated, one of SecConst.TOKEN constants
    * @param hardwaretokenissuerid , if token should be hard, the id of the hard token issuer, else 0.
    */
   public void addUser( se.anatom.ejbca.log.Admin admin,java.lang.String username,java.lang.String password,java.lang.String subjectdn,java.lang.String subjectaltname,java.lang.String email,boolean clearpwd,int endentityprofileid,int certificateprofileid,int type,int tokentype,int hardwaretokenissuerid,int caid )
      throws se.anatom.ejbca.authorization.AuthorizationDeniedException, se.anatom.ejbca.ra.raadmin.UserDoesntFullfillEndEntityProfile, javax.ejb.DuplicateKeyException, java.rmi.RemoteException;

   /**
    * Changes data for a user in the database speciefied by username.
    * @param username the unique username.
    * @param password the password used for authentication.*
    * @param subjectdn the DN the subject is given in his certificate.
    * @param subjectaltname the Subject Alternative Name to be used.
    * @param email the email of the subject or null.
    * @param endentityprofileid the id number of the end entity profile bound to this user.
    * @param certificateprofileid the id number of the certificate profile that should be generated for the user.
    * @param type of user i.e administrator, keyrecoverable and/or sendnotification
    * @param tokentype the type of token to be generated, one of SecConst.TOKEN constants
    * @param hardwaretokenissuerid if token should be hard, the id of the hard token issuer, else 0.
    * @param caid the id of the CA that should be used to issue the users certificate
    * @throws EJBException if a communication or other error occurs.
    */
   public void changeUser( se.anatom.ejbca.log.Admin admin,java.lang.String username,java.lang.String password,java.lang.String subjectdn,java.lang.String subjectaltname,java.lang.String email,boolean clearpwd,int endentityprofileid,int certificateprofileid,int type,int tokentype,int hardwaretokenissuerid,int status,int caid )
      throws se.anatom.ejbca.authorization.AuthorizationDeniedException, se.anatom.ejbca.ra.raadmin.UserDoesntFullfillEndEntityProfile, java.rmi.RemoteException;

   /**
    * Deletes a user from the database. The users certificates must be revoked BEFORE this method is called.
    * @param username the unique username.
    * @throws NotFoundException if the user does not exist
    * @throws RemoveException if the user could not be removed
    */
   public void deleteUser( se.anatom.ejbca.log.Admin admin,java.lang.String username )
      throws se.anatom.ejbca.authorization.AuthorizationDeniedException, se.anatom.ejbca.ra.exception.NotFoundException, javax.ejb.RemoveException, java.rmi.RemoteException;

   /**
    * Changes status of a user.
    * @param username the unique username.
    * @param status the new status, from 'UserData'.
    */
   public void setUserStatus( se.anatom.ejbca.log.Admin admin,java.lang.String username,int status )
      throws se.anatom.ejbca.authorization.AuthorizationDeniedException, javax.ejb.FinderException, java.rmi.RemoteException;

   /**
    * Sets a new password for a user.
    * @param admin the administrator pwrforming the action
    * @param username the unique username.
    * @param password the new password for the user, NOT null.
    */
   public void setPassword( se.anatom.ejbca.log.Admin admin,java.lang.String username,java.lang.String password )
      throws se.anatom.ejbca.ra.raadmin.UserDoesntFullfillEndEntityProfile, se.anatom.ejbca.authorization.AuthorizationDeniedException, javax.ejb.FinderException, java.rmi.RemoteException;

   /**
    * Sets a clear text password for a user.
    * @param admin the administrator pwrforming the action
    * @param username the unique username.
    * @param password the new password to be stored in clear text. Setting password to 'null' effectively deletes any previous clear text password.
    */
   public void setClearTextPassword( se.anatom.ejbca.log.Admin admin,java.lang.String username,java.lang.String password )
      throws se.anatom.ejbca.ra.raadmin.UserDoesntFullfillEndEntityProfile, se.anatom.ejbca.authorization.AuthorizationDeniedException, javax.ejb.FinderException, java.rmi.RemoteException;

   /**
    * Verifies a password for a user.
    * @param admin the administrator pwrforming the action
    * @param username the unique username.
    * @param password the password to be verified.
    */
   public boolean verifyPassword( se.anatom.ejbca.log.Admin admin,java.lang.String username,java.lang.String password )
      throws se.anatom.ejbca.ra.raadmin.UserDoesntFullfillEndEntityProfile, se.anatom.ejbca.authorization.AuthorizationDeniedException, javax.ejb.FinderException, java.rmi.RemoteException;

   /**
    * Method that revokes a user.
    * @param username the username to revoke.
    */
   public void revokeUser( se.anatom.ejbca.log.Admin admin,java.lang.String username,int reason )
      throws se.anatom.ejbca.authorization.AuthorizationDeniedException, javax.ejb.FinderException, java.rmi.RemoteException;

   /**
    * Method that revokes a certificate.
    * @param certserno the serno of certificate to revoke.
    * @param username the username to revoke.
    * @param reason the reason of revokation.
    */
   public void revokeCert( se.anatom.ejbca.log.Admin admin,java.math.BigInteger certserno,java.lang.String issuerdn,java.lang.String username,int reason )
      throws se.anatom.ejbca.authorization.AuthorizationDeniedException, javax.ejb.FinderException, java.rmi.RemoteException;

   /**
    * Finds a user.
    * @param admin the administrator pwrforming the action
    * @param username username.
    * @return UserAdminData or null if the user is not found.
    */
   public se.anatom.ejbca.ra.UserAdminData findUser( se.anatom.ejbca.log.Admin admin,java.lang.String username )
      throws javax.ejb.FinderException, se.anatom.ejbca.authorization.AuthorizationDeniedException, java.rmi.RemoteException;

   /**
    * Finds a user by its subjectDN.
    * @param subjectdn
    * @return UserAdminData or null if the user is not found.
    */
   public se.anatom.ejbca.ra.UserAdminData findUserBySubjectDN( se.anatom.ejbca.log.Admin admin,java.lang.String subjectdn,java.lang.String issuerdn )
      throws se.anatom.ejbca.authorization.AuthorizationDeniedException, java.rmi.RemoteException;

   /**
    * Finds a user by its Email.
    * @param email
    * @return UserAdminData or null if the user is not found.
    */
   public java.util.Collection findUserByEmail( se.anatom.ejbca.log.Admin admin,java.lang.String email )
      throws se.anatom.ejbca.authorization.AuthorizationDeniedException, java.rmi.RemoteException;

   /**
    * Method that checks if user with specified users certificate exists in database and is set as administrator.
    * @param subjectdn
    * @throws AuthorizationDeniedException if user isn't an administrator.
    */
   public void checkIfCertificateBelongToAdmin( se.anatom.ejbca.log.Admin admin,java.math.BigInteger certificatesnr,java.lang.String issuerdn )
      throws se.anatom.ejbca.authorization.AuthorizationDeniedException, java.rmi.RemoteException;

   /**
    * Finds all users with a specified status.
    * @param status the new status, from 'UserData'.
    * @return Collection of UserAdminData
    */
   public java.util.Collection findAllUsersByStatus( se.anatom.ejbca.log.Admin admin,int status )
      throws javax.ejb.FinderException, java.rmi.RemoteException;

   /**
    * Finds all users and returns the first MAXIMUM_QUERY_ROWCOUNT.
    * @return Collection of UserAdminData
    */
   public java.util.Collection findAllUsersWithLimit( se.anatom.ejbca.log.Admin admin )
      throws javax.ejb.FinderException, java.rmi.RemoteException;

   /**
    * Finds all users with a specified status and returns the first MAXIMUM_QUERY_ROWCOUNT.
    * @param status the new status, from 'UserData'.
    */
   public java.util.Collection findAllUsersByStatusWithLimit( se.anatom.ejbca.log.Admin admin,int status,boolean onlybatchusers )
      throws javax.ejb.FinderException, java.rmi.RemoteException;

   /**
    * Starts an external service that may be needed bu user administration.
    */
   public void startExternalService( java.lang.String[] args )
      throws java.rmi.RemoteException;

   /**
    * Method to execute a customized query on the ra user data. The parameter query should be a legal Query object.
    * @param query a number of statments compiled by query class to a SQL 'WHERE'-clause statment.
    * @param caauthorizationstring is a string placed in the where clause of SQL query indication which CA:s the administrator is authorized to view.
    * @param endentityprofilestring is a string placed in the where clause of SQL query indication which endentityprofiles the administrator is authorized to view.
    * @return a collection of UserAdminData. Maximum size of Collection is defined i IUserAdminSessionRemote.MAXIMUM_QUERY_ROWCOUNT
    * @throws IllegalQueryException when query parameters internal rules isn't fullfilled.
    * @see se.anatom.ejbca.util.query.Query
    */
   public java.util.Collection query( se.anatom.ejbca.log.Admin admin,se.anatom.ejbca.util.query.Query query,java.lang.String caauthorizationstring,java.lang.String endentityprofilestring )
      throws se.anatom.ejbca.util.query.IllegalQueryException, java.rmi.RemoteException;

   /**
    * Methods that checks if a user exists in the database having the given endentityprofileid. This function is mainly for avoiding desyncronisation when a end entity profile is deleted.
    * @param endentityprofileid the id of end entity profile to look for.
    * @return true if endentityprofileid exists in userdatabase.
    */
   public boolean checkForEndEntityProfileId( se.anatom.ejbca.log.Admin admin,int endentityprofileid )
      throws java.rmi.RemoteException;

   /**
    * Methods that checks if a user exists in the database having the given certificateprofileid. This function is mainly for avoiding desyncronisation when a certificateprofile is deleted.
    * @param certificateprofileid the id of certificateprofile to look for.
    * @return true if certificateproileid exists in userdatabase.
    */
   public boolean checkForCertificateProfileId( se.anatom.ejbca.log.Admin admin,int certificateprofileid )
      throws java.rmi.RemoteException;

   /**
    * Methods that checks if a user exists in the database having the given caid. This function is mainly for avoiding desyncronisation when a CAs is deleted.
    * @param caid the id of CA to look for.
    * @return true if caid exists in userdatabase.
    */
   public boolean checkForCAId( se.anatom.ejbca.log.Admin admin,int caid )
      throws java.rmi.RemoteException;

   /**
    * Methods that checks if a user exists in the database having the given hard token profile id. This function is mainly for avoiding desyncronisation when a hard token profile is deleted.
    * @param profileid of hardtokenprofile to look for.
    * @return true if proileid exists in userdatabase.
    */
   public boolean checkForHardTokenProfileId( se.anatom.ejbca.log.Admin admin,int profileid )
      throws java.rmi.RemoteException;

   /**
    * Method checking if username already exists in database.
    * @return true if username already exists.
    */
   public boolean existsUser( se.anatom.ejbca.log.Admin admin,java.lang.String username )
      throws java.rmi.RemoteException;

}
