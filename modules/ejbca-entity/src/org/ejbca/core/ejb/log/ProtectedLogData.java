/*************************************************************************
 *                                                                       *
 *  EJBCA: The OpenSource Certificate Authority                          *
 *                                                                       *
 *  This software is free software; you can redistribute it and/or       *
 *  modify it under the terms of the GNU Lesser General Public           *
 *  License as published by the Free Software Foundation; either         *
 *  version 2.1 of the License, or any later version.                    *
 *                                                                       *
 *  See terms of license at gnu.org.                                     *
 *                                                                       *
 *************************************************************************/

package org.ejbca.core.ejb.log;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.ejbca.core.model.log.ProtectedLogEventIdentifier;
import org.ejbca.core.model.log.ProtectedLogEventRow;
import org.ejbca.util.Base64;
import org.ejbca.util.GUIDGenerator;
import org.ejbca.util.StringTools;

/**
 * Representation of a log entry in the database.
 * 
 * @version $Id$
 */
@Deprecated
@Entity
@Table(name="ProtectedLogData")
public class ProtectedLogData implements Serializable {

	private static final long serialVersionUID = 1L;
	//private static final Logger log = Logger.getLogger(ProtectedLogData.class);

	private String pk;
	private int adminType;
	private String adminData;
	private int cAId;
	private int module;
	private long eventTime;
	private String username;
	private String certificateSerialNumber;
	private String certificateIssuerDN;
	private int eventId;
	private String eventComment;
	private int nodeGUID;
	private long counter;
	private String nodeIP;
	private String b64LinkedInEventIdentifiers;
	private String b64LinkedInEventsHash;
	private String currentHashAlgorithm;
	private int protectionKeyIdentifier;
	private String protectionKeyAlgorithm;
	private String b64Protection;
	
	/**
	 * Entity Bean holding data of a service configuration.
	 */
	public ProtectedLogData(int adminType, String adminData, int caid, int module, long eventTime, String username, String certificateSerialNumber,
			String certificateIssuerDN, int eventId, String eventComment, ProtectedLogEventIdentifier eventIdentifier, String nodeIP, ProtectedLogEventIdentifier[] linkedInEventIdentifiers,
			byte[] linkedInEventsHash, String currentHashAlgorithm, int protectionKeyIdentifier, String protectionKeyAlgorithm, byte[] protection) {
		setPk(GUIDGenerator.generateGUID(this));
	    setAdminType(adminType);
	    setAdminData(adminData);
	    setCaId(caid);
	    setModule(module);
	    setEventTime(eventTime);
	    setUsername(StringTools.strip(username));
	    setCertificateSerialNumber(certificateSerialNumber);
	    setCertificateIssuerDN(certificateIssuerDN);
	    setEventId(eventId);
	    setEventComment(eventComment);
	    setNodeGUID(eventIdentifier.getNodeGUID());
	    setCounter(eventIdentifier.getCounter());
	    setNodeIP(nodeIP);
	    setLinkedInEventIdentifiers(linkedInEventIdentifiers);
	    setLinkedInEventsHash(linkedInEventsHash);
	    setCurrentHashAlgorithm(currentHashAlgorithm);
	    setProtectionKeyIdentifier(protectionKeyIdentifier);
	    setProtectionKeyAlgorithm(protectionKeyAlgorithm);
	    setProtection(protection);
	}

	public ProtectedLogData() { }
	
	/** Primary Key. A 32 byte GUID generated by org.ejbca.util.GUIDGenerator. */
	@Id
	@Column(name="pk")
    public String getPk() { return pk; }
    public void setPk(String pk) { this.pk = pk; }

    /** The type of data stored in the admindata field, should be one of org.ejbca.core.model.log.Admin.TYPE_ constants. */
	@Column(name="adminType", nullable=false)
    public int getAdminType() { return adminType; }
    public void setAdminType(int adminType) { this.adminType = adminType; }

    /** The data identifying the administrator, should be certificate snr or ip-address when no certificate could be retrieved. */
	@Column(name="adminData")
    public String getAdminData() { return adminData; }
    public void setAdminData(String adminData) { this.adminData = adminData; }
    
    /** Id of the CA performing the event. */
	@Column(name="cAId", nullable=false)
    public int getCaId() { return cAId; }
    public void setCaId(int cAId) { this.cAId = cAId; }

    /** The module (CA,RA ...) using the logsession bean. */
	@Column(name="module", nullable=false)
    public int getModule() { return module; }
    public void setModule(int module) { this.module = module; }

    /** Time the event occurred. */
	@Column(name="eventTime", nullable=false)
    public long getEventTime() { return eventTime; }
    public void setEventTime(long eventTime) { this.eventTime = eventTime; }

    /** The name of the user involved or null if no user is involved. */
	@Column(name="username")
    public String getUsername() { return username; }
    /** username must be called 'stripped' using StringTools.strip()  */
    public void setUsername(String username) { this.username = username; }

    /** The serial number of the certificate involved in the event or null if no certificate is involved. */
	@Column(name="certificateSerialNumber")
    public String getCertificateSerialNumber() { return certificateSerialNumber; }
    public void setCertificateSerialNumber(String certificateSerialNumber) { this.certificateSerialNumber = certificateSerialNumber; }

    /** The issuers DN of the certificate involved in the event or null if no certificate is involved. */
	@Column(name="certificateIssuerDN")
    public String getCertificateIssuerDN() { return certificateIssuerDN; }
    public void setCertificateIssuerDN(String certificateIssuerDN) { this.certificateIssuerDN = certificateIssuerDN; }
    
    /** Id of the event, should be one of the org.ejbca.core.model.log.LogConstants.EVENT_ constants. */
    @Column(name="eventId", nullable=false)
    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }

    /** An optional comment of the event. */
	// DB2: VARCHAR(4000) [4000], Derby: VARCHAR(32672) [32672 chars], Informix: TEXT (2147483648 b?), Ingres: , MSSQL: TEXT [2,147,483,647 bytes], MySQL: TEXT [65535 chars], Oracle: VARCHAR2(4000) [4000 chars], Sybase: TEXT [2,147,483,647 chars]  
	@Column(name="eventComment", length=4000)
	//@Lob
    public String getEventComment() { return eventComment; }
    public void setEventComment(String eventComment) { this.eventComment = eventComment; }

    /** The current node ID. */
    @Column(name="nodeGUID", nullable=false)
    public int getNodeGUID() { return nodeGUID; }
    public void setNodeGUID(int nodeGUID) { this.nodeGUID = nodeGUID; }

    /** A sequential number. Together with GUID a unique identifier for this log-row. */
    @Column(name="counter", nullable=false)
    public long getCounter() { return counter; }
    public void setCounter(long counter) { this.counter = counter; }
    
    @Column(name="nodeIP")
    public String getNodeIP() { return nodeIP; }
    public void setNodeIP(String nodeIP) { this.nodeIP = nodeIP; }
    
    /** A collection of (nodeGUID, counter) pairs of linked in rows or null if none were linked to. */
	// DB2: CLOB(100K) [100K (2GBw/o)], Derby: VARCHAR(32672) [32672 chars], Informix: TEXT (2147483648 b?), Ingres: , MSSQL: TEXT [2,147,483,647 bytes], MySQL: TEXT [65535 chars], Oracle: CLOB [4G chars], Sybase: TEXT [2,147,483,647 chars]
	@Column(name="b64LinkedInEventIdentifiers", length=32672)
	@Lob
    public String getB64LinkedInEventIdentifiers() { return b64LinkedInEventIdentifiers; }
    public void setB64LinkedInEventIdentifiers(String b64LinkedInEventIdentifiers) { this.b64LinkedInEventIdentifiers = b64LinkedInEventIdentifiers; }

    @Transient
    public ProtectedLogEventIdentifier[] getLinkedInEventIdentifiers() {
    	String b64LinkedInEventIdentifiers = getB64LinkedInEventIdentifiers();
    	ProtectedLogEventIdentifier[] protectedLogEventIdentifiers = null;
    	if (b64LinkedInEventIdentifiers != null) {
        	String[] b64LinkedInEventIdentifierArray = b64LinkedInEventIdentifiers.split(";");
        	protectedLogEventIdentifiers = new ProtectedLogEventIdentifier[b64LinkedInEventIdentifierArray.length];
    		for (int i=0; i<b64LinkedInEventIdentifierArray.length; i++) {
    			protectedLogEventIdentifiers[i] = new ProtectedLogEventIdentifier(b64LinkedInEventIdentifierArray[i]);
    		}
    	}
    	return protectedLogEventIdentifiers;
    }

    public void setLinkedInEventIdentifiers(ProtectedLogEventIdentifier[] linkedInEventIdentifiers) {
    	if (linkedInEventIdentifiers != null) {
    		String b64LinkedInEventIdentifiers = null;
        	for (int i=0; i<linkedInEventIdentifiers.length; i++) {
        		if (b64LinkedInEventIdentifiers == null) {
        			b64LinkedInEventIdentifiers = linkedInEventIdentifiers[i].getAsBase64EncodedString();
        		} else {
        			b64LinkedInEventIdentifiers += ";" + linkedInEventIdentifiers[i].getAsBase64EncodedString();
        		}
        	}
        	setB64LinkedInEventIdentifiers(b64LinkedInEventIdentifiers);
    	} else {
    		setB64LinkedInEventIdentifiers(null);
    	}
    }

    /** The hash of ( The row for each linkedInEventIdentifiers ) */
    @Column(name="b64LinkedInEventsHash")
    public String getB64LinkedInEventsHash() { return b64LinkedInEventsHash; }
    public void setB64LinkedInEventsHash(String b64LinkedInEventsHash) { this.b64LinkedInEventsHash = b64LinkedInEventsHash; }
    
    @Transient
    public byte[] getLinkedInEventsHash() {
    	String b64LinkedInEventsHash = getB64LinkedInEventsHash();
    	if (b64LinkedInEventsHash != null) {
        	return Base64.decode(b64LinkedInEventsHash.getBytes());
    	}
    	return null;
    }

    public void setLinkedInEventsHash(byte[] linkedInEventsHash) {
    	if (linkedInEventsHash != null) {
        	setB64LinkedInEventsHash(new String(Base64.encode(linkedInEventsHash, false)));
    	} else {
        	setB64LinkedInEventsHash(null);
    	}
    }

    /** The hash algorithm that should be used to calculate the hash of this LogEventRow and producing b64LinkedInEventsHash. */
    @Column(name="currentHashAlgorithm")
    public String getCurrentHashAlgorithm() { return currentHashAlgorithm; }
    public void setCurrentHashAlgorithm(String currentHashAlgorithm) { this.currentHashAlgorithm = currentHashAlgorithm; }

    /** The identifier for the key used to protect the current row or null if the row is unprotected. */
    @Column(name="protectionKeyIdentifier", nullable=false)
    public int getProtectionKeyIdentifier() { return protectionKeyIdentifier; }
    public void setProtectionKeyIdentifier(int protectionKeyIdentifier) { this.protectionKeyIdentifier = protectionKeyIdentifier; }

    /** The algorithm identifier used to protect this row or null if the row is unprotected. */
    @Column(name="protectionKeyAlgorithm")
    public String getProtectionKeyAlgorithm() { return protectionKeyAlgorithm; }
    public void setProtectionKeyAlgorithm(String protectionKeyAlgorithm) { this.protectionKeyAlgorithm = protectionKeyAlgorithm; }

    /** The signature of all the previous columns or null if the row is unprotected. */
	// DB2: VARCHAR(4000) [4000], Derby: Derby: LONG VARCHAR [32,700 characters], Informix: TEXT (2147483648 b?), Ingres: , MSSQL: TEXT [2,147,483,647 bytes], MySQL: TEXT [65535 chars], Oracle: VARCHAR2(4000) [4000 chars], Sybase: TEXT [2,147,483,647 chars]  
	@Column(name="b64Protection", length=4000)
	//@Lob
    public String getB64Protection() { return b64Protection; }
    public void setB64Protection(String b64Protection) { this.b64Protection = b64Protection; }

    @Transient
    public byte[] getProtection() {
    	String b64Protection = getB64Protection();
    	if (b64Protection != null) {
        	return Base64.decode(b64Protection.getBytes());
    	}
    	return null;
    }

    public void setProtection(byte[] protection) {
    	if (protection != null) {
        	setB64Protection(new String(Base64.encode(protection, false)));
    	} else {
        	setB64Protection(null);
    	}
    }

    public ProtectedLogEventRow toProtectedLogEventRow() {
    	return new ProtectedLogEventRow(getAdminType(), getAdminData(), getCaId(), getModule(), getEventTime(),
    			getUsername(), getCertificateSerialNumber(), getCertificateIssuerDN(), getEventId(), getEventComment(),
    			new ProtectedLogEventIdentifier(getNodeGUID(), getCounter()), getNodeIP(), getLinkedInEventIdentifiers(), getLinkedInEventsHash(),
    			getCurrentHashAlgorithm(), getProtectionKeyIdentifier(), getProtectionKeyAlgorithm(), getProtection());
    }

	//
	// Search functions. 
	//

	/** @return the found entity instance or null if the entity does not exist */
	public static ProtectedLogData findById(EntityManager entityManager, String pk) {
		return entityManager.find(ProtectedLogData.class, pk);
	}

	/**
	 * @throws NonUniqueResultException if more than one entity with the name exists
	 * @return the found entity instance or null if the entity does not exist
	 */
	public static ProtectedLogData findByNodeGUIDandCounter(EntityManager entityManager, int nodeGUID, long counter) {
		ProtectedLogData ret = null;
		try {
			Query query = entityManager.createQuery("SELECT a FROM ProtectedLogData a WHERE a.nodeGUID=:nodeGUID AND a.counter=:counter");
			query.setParameter("nodeGUID", nodeGUID);
			query.setParameter("counter", counter);
			ret = (ProtectedLogData) query.getSingleResult();
		} catch (NoResultException e) {
		}
		return ret;
	}    

	/** @return return the query results as a List. */
	public static List<ProtectedLogData> findNewProtectedLogEvents(EntityManager entityManager, int nodeToExclude, long newerThan) {
		Query query = entityManager.createQuery("SELECT a FROM ProtectedLogData a WHERE a.nodeGUID<>:nodeToExclude AND a.eventTime>=:newerThan AND a.b64Protection IS NOT NULL");
		query.setParameter("nodeToExclude", nodeToExclude);
		query.setParameter("newerThan", newerThan);
		return query.getResultList();
	}    

	/** @return return the query results as a List. */
	public static List<ProtectedLogData> findProtectedLogEventsByTime(EntityManager entityManager, long eventTime) {
		Query query = entityManager.createQuery("SELECT a FROM ProtectedLogData a WHERE a.eventTime=:eventTime");
		query.setParameter("eventTime", eventTime);
		return query.getResultList();
	}    

	/**
	 * Using this method would probably send a database to a long and painful near death experience.. =/
	 * @return return the query results as a List.
	 */
	public static List<ProtectedLogData> findAll(EntityManager entityManager) {
		Query query = entityManager.createQuery("SELECT a FROM ProtectedLogData a");
		return query.getResultList();
	}    
}
