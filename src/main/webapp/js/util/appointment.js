'use strict';

if (!window.Oscar) window.Oscar = {};
if (!Oscar.Util) Oscar.Util = {};

Oscar.Util.Appointment = {};

Oscar.Util.Appointment.TimeFormats = ["H:m", "Hmm", "H"];
Oscar.Util.Appointment.TimeFormatDisplay = "HH:mm";

/**
 * If the input value represents a valid time, normalizes it to the display format,
 * and returns true. Otherwise returns false.
 *
 * @param {object} elem   The DOM element to validate.
 *
 * @returns {boolean}
 */

Oscar.Util.Appointment.validateTimeInput = function validateTimeInput(elem)
{
	var timeStr = elem.value.trim();
	var time = moment(timeStr, this.TimeFormats, true);

	if (!time.isValid())
	{
		return false;
	}

	elem.value = time.format(this.TimeFormatDisplay);
	return true;
};

/**
 * Given a start time and duration, calculates the end time and sets it as the value of the given
 * input using the H:m format.
 * If the calculated end time is determined to fall on a different day, fails and returns false.
 *
 * @param {object} endElem   The DOM element for the end time input to be set.
 * @param {string} start   The start time to use, in the H:m format.
 * @param {string} duration   The duration to use, in minutes.
 *
 * @returns {boolean}
 */
Oscar.Util.Appointment.setEndTime = function setEndTime(endElem, start, duration)
{
	// if duration is unparseable or 0, use 1
	var durationTime = parseInt(duration) || 1;
	var startTime = moment(start, this.TimeFormats);
	var endTime = startTime.clone();

	endTime.add(Math.abs(durationTime) - 1, 'minutes');
	if (endTime.diff(startTime, 'days') !== 0)
	{
		return false;
	}

	endElem.value = endTime.format(this.TimeFormatDisplay);
	return true;
};
