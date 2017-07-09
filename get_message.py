#!/usr/bin/python

DATABASE_PATH = '/var/mobile/Library/SMS/sms.db';

GET_MESSAGE_SQL_QUERY = "SELECT m.rowid, c.chat_identifier,m.text FROM message m INNER JOIN chat_message_join cmj ON cmj.message_id=m.rowid INNER JOIN chat c ON cmj.chat_id=c.rowid WHERE cmj.message_id=";


import cgi;
import subprocess;

print "Content-Type: application/json\n\n";

def sanitizeAndSplitSqliteOutput(sqliteOutput):
    return sqliteOutput.replace('"','%22')[:-1].split('|',2);

form = cgi.FieldStorage();

if 'message_id' not in form:
    print """ { "error": "Cannot find message_id parameter" } """;
    exit(1);

sqliteProcess = subprocess.Popen(["sqlite3",DATABASE_PATH,GET_MESSAGE_SQL_QUERY + form['message_id'].value ], stdout=subprocess.PIPE);

output,error = sqliteProcess.communicate();

result = sanitizeAndSplitSqliteOutput(output);


if len(result) > 1:
    print """ { "error": """, '"' + str(error) + '"',',';
    print """ "id":""", '"' + result[0] + '"',',';
    print """ "chat_identifier":""", '"' + result[1] + '"',',';
    print """ "message":""",'"' + result[2] + '"';
    print """}""";
else:
    print """ { "error": "Not Found" }""";
