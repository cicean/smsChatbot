#!/usr/bin/python

DATABASE_PATH = '/var/mobile/Library/SMS/sms.db';

GET_MESSAGE_SQL_QUERY = "SELECT m.rowid, c.chat_identifier,m.is_from_me,m.date,m.text FROM message m INNER JOIN chat_message_join cmj ON cmj.message_id=m.rowid INNER JOIN chat c ON cmj.chat_id=c.rowid WHERE cmj.message_id=";


import cgi;
import subprocess;

print "Content-Type: application/json\n\n";

BACKSLASH = chr(92);
BACKSPACE = chr(8);
FORM_FEED = chr(12);
CARRETURN = chr(13);
TAB_CHAR = chr(9);
DOUBLE_QUOTE = '"';
NEWLINE = '\n';


def sanitizeAndSplitSqliteOutput(sqliteOutput):
    return sqliteOutput[:-1].replace(BACKSLASH,BACKSLASH + BACKSLASH).replace(FORM_FEED,BACKSLASH + "f").replace(NEWLINE, BACKSLASH + "n").replace(TAB_CHAR, BACKSLASH + 't').replace(DOUBLE_QUOTE, BACKSLASH + DOUBLE_QUOTE).replace(CARRETURN, BACKSLASH + 'r').replace(BACKSPACE, BACKSLASH + 'b').split('|',4);

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
    print """ "chat_identifier":""", '"' + result[1].replace('+','') + '"',',';
    print """ "is_from_me":""", '"' + str(result[2] == "1").lower() + '"', ',';
    print """ "date":""", '"' + result[3] + '"',',';
    print """ "message":""",'"' + result[4] + '"';
    print """}""";
else:
    print """ { "error": "Not Found" }""";
