/**
 * Copyright (c) 2006-. OSCARservice, OpenSoft System. All Rights Reserved.
 * This software is published under the GPL GNU General Public License.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.oscarehr.common.dao;

import java.util.List;

import javax.persistence.Query;

import org.oscarehr.common.model.Billingreferral;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Toby
 */
@Deprecated
@Repository
public class BillingreferralDao extends AbstractDao<Billingreferral> {

	public BillingreferralDao() {
		super(Billingreferral.class);
	}

	public Billingreferral getByReferralNo(String referral_no) {
		if (referral_no == null || referral_no.isEmpty())
		{
			return null;
		}
		 String sql = "select br From Billingreferral br WHERE br.referralNo=?";
		 Query query = entityManager.createQuery(sql);
		 query.setParameter(1, referral_no);

		 @SuppressWarnings("unchecked")
		 List<Billingreferral> brs = query.getResultList();
		 if(!brs.isEmpty())
			 return brs.get(0);
		 return null;
	 }

	 public Billingreferral getById(int id) {
		 return find(id);
	 }

	 public List<Billingreferral> getBillingreferrals() {
		 String sql = "SELECT br From Billingreferral br ORDER BY  br.lastName, br.firstName";
		 Query query = entityManager.createQuery(sql);

		 @SuppressWarnings("unchecked")
		 List<Billingreferral> brs = query.getResultList();

		 return brs;
	 }

    public List<Billingreferral> getBillingreferral(String referral_no) {
    	String sql = "SELECT br FROM Billingreferral br WHERE br.referralNo= :referralNo";
		Query query = entityManager.createQuery(sql);
		query.setParameter("referralNo", referral_no);

		@SuppressWarnings("unchecked")
		List<Billingreferral> cList = query.getResultList();
		return cList;
    }

    public List<Billingreferral> getBillingreferral(String last_name, String first_name) {

		List<Billingreferral> cList = findByFullNameAndReferralNo("%"+last_name, "%"+first_name, null, null, null);

        if (cList != null && cList.size() > 0) {
            return cList;
        } else {
            return null;
        }
    }

	public List<Billingreferral> findByFullNameAndReferralNo(String lastName, String firstName, String referralNo, Integer offset, Integer maxResults)
	{
		// set up the query
		String queryString =
				"SELECT x FROM " + modelClass.getSimpleName() + " x " +
						"WHERE 1=1 ";

		if (lastName != null)
			queryString += "AND ( x.lastName LIKE :lastName ) ";
		if (firstName != null)
			queryString += "AND ( x.firstName LIKE :firstName ) ";
		if (referralNo != null)
			queryString += "AND ( x.referralNo LIKE :refNo ) ";

		queryString += "ORDER BY x.lastName, x.firstName";

		Query query = entityManager.createQuery(queryString);

		// set parameters
		if (lastName != null)
			query.setParameter("lastName", lastName + "%");
		if (firstName != null)
			query.setParameter("firstName", firstName + "%");
		if (referralNo != null)
			query.setParameter("refNo", referralNo + "%");
		if (offset != null)
			query.setFirstResult(offset);
		if (maxResults != null)
			query.setMaxResults(maxResults);

		@SuppressWarnings("unchecked")
		List<Billingreferral> results = query.getResultList();
		return results;

	}

	public List<Billingreferral> getBillingreferralByLastName(String last_name, int limit, int offset)
	{
		String sql = "SELECT br FROM Billingreferral br WHERE br.lastName LIKE :lastName ORDER BY br.lastName";
		Query query = entityManager.createQuery(sql);
		query.setParameter("lastName", "%" + last_name + "%");

		query.setMaxResults(limit);
		query.setFirstResult(offset);

		@SuppressWarnings("unchecked")
		List<Billingreferral> cList = query.getResultList();
		return cList;
	}


    public List<Billingreferral> getBillingreferralBySpecialty(String specialty) {
    	String sql = "SELECT br From Billingreferral br WHERE br.specialty like ? order by br.lastName";
		Query query = entityManager.createQuery(sql);
		query.setParameter(1, "%"+specialty+"%");

		@SuppressWarnings("unchecked")
		List<Billingreferral> cList = query.getResultList();

        if (cList != null && cList.size() > 0) {
            return cList;
        } else {
            return null;
        }
    }

    /*
     * Don't blame me for this one, converted from SQL.
     */
     public List<Billingreferral> searchReferralCode(String codeName, String codeName1, String codeName2, String desc, String fDesc, String desc1, String fDesc1, String desc2, String fDesc2) {
    	String sql = "SELECT b FROM Billingreferral b WHERE b.referralNo LIKE ? or b.referralNo LIKE ? or b.referralNo LIKE ?"
        		+ " or (b.lastName LIKE ? and b.firstName LIKE ?)"
        		+ " or (b.lastName LIKE ? and b.firstName LIKE ?)"
        		+ " or (b.lastName LIKE ? and b.firstName LIKE ?)";

		Query query = entityManager.createQuery(sql);
		query.setParameter(1, codeName);
		query.setParameter(2, codeName1);
		query.setParameter(3, codeName2);
		query.setParameter(4, desc);
		query.setParameter(5, fDesc);
		query.setParameter(6, desc1);
		query.setParameter(7, fDesc1);
		query.setParameter(8, desc2);
		query.setParameter(9, fDesc2);


		@SuppressWarnings("unchecked")
		List<Billingreferral> cList = query.getResultList();

        return cList;
    }

    public void updateBillingreferral(Billingreferral obj) {
    	if(obj.getBillingreferralNo() == null || obj.getBillingreferralNo().intValue() == 0) {
    		persist(obj);
    	} else {
    		merge(obj);
    	}
    }

	 public String getReferralDocName(String referral_no) {
		 Billingreferral br = this.getByReferralNo(referral_no);

		 if(br != null)
			 return br.getLastName() + ", " + br.getFirstName();

		 return "";
	 }
}
