/**
 * Copyright (c) 2012-2018. CloudPractice Inc. All Rights Reserved.
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
 *
 * This software was written for
 * CloudPractice Inc.
 * Victoria, British Columbia
 * Canada
 */

package org.oscarehr.ws.external.soap.v1.transfer.schedule.bookingrules;

import org.json.simple.JSONObject;
import org.oscarehr.common.model.Appointment;
import org.oscarehr.schedule.model.ScheduleSearchResult;

public abstract class BookingRule implements Comparable<BookingRule>
{
    protected BookingRule(BookingRuleType ruletype, String jsonType)
    {
        this.ruleType = ruletype;
        this.jsonType = jsonType;
    }

    protected String jsonType;
    private BookingRuleType ruleType;

    /**
     * Determines if the appointment violates this booking rule.
     * @param appointment The appointment to validate
     * @return true if rule is violated
     */
    public abstract Boolean isViolated(Appointment appointment);

    /**
     * Determines if the schedule slot would violate the booking rule if an appointment were to be booked into it.
     * @param result
     * @return true if rule is violated
     */
    public abstract Boolean isViolated(ScheduleSearchResult result);

    /**
     * Transform this rule into a JSON object for serialization which is compatible with MyHealthAccess schema
     * @return JSON representation
     */
    public abstract JSONObject toJSON();

    /**
     * Return the type of booking rule
     * @return Enumerated rule type
     */
    public BookingRuleType getType()
    {
        return this.ruleType;
    }

    /**
     * Collections of BookingRules can be sorted based on the order in which they should be applied
     * @param o Another booking rule
     * @return order comparison
     */
    public int compareTo(BookingRule o)
    {
        return Integer.compare(this.ruleType.ordinal(), o.getType().ordinal());
    }
}
