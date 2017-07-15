#!/bin/sh
DATABASE_PATH=/var/mobile/Library/SMS/sms.db
CALLBACK_URL="http://whitecat22.internal.lambdanum.com:5000/sms_received"

new_index=$(sqlite3 $DATABASE_PATH "select seq from sqlite_sequence where name='message'");
last_index=$new_index;

while :
do
    new_index=$(sqlite3 $DATABASE_PATH "select seq from sqlite_sequence where name='message'");

    if [ $new_index -gt $last_index ];
    then
        last_index=$(($last_index+1));

        if [ $(sqlite3 $DATABASE_PATH "select is_from_me from message where rowid=$last_index") -eq 0 ];
        then
            # echo 'A new SMS has just arrived.';
            # echo $last_index;
	    curl -d id=$last_index -s $CALLBACK_URL > /dev/null;
        fi;
    else
        sleep 1;
    fi;
done
