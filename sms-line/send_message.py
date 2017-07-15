#!/usr/bin/python

print "Content-Type: application/json\n\n";

import cgi,os,sys;
import subprocess;

form = cgi.FieldStorage();

if 'destination' not in form:
    print '{ "error": "Cannot find destination" }';
    exit(1);
if 'message' not in form:
    print '{ "error: "Cannot find message" }';
    exit(1);

destination = form['destination'].value;
message = form['message'].value;

SEND_SMS_COMMAND_TEMPLATE = '/Applications/biteSMS.app/biteSMS -send -carrier %s';

smsProcess = subprocess.Popen((SEND_SMS_COMMAND_TEMPLATE % (destination)).split() + [message], stdout=subprocess.PIPE);
output,error = smsProcess.communicate();

if output == "" and (error == "" or error is None):
    print '{ "error": "None" }';
else:
    print '{ "error": "Unexpected error" }';
