#!/bin/sh

scp send_message.py root@sms-line1.internal.lambdanum.com:/var/www/send_message.py
scp get_message.py root@sms-line1.internal.lambdanum.com:/var/www/get_message.py
scp sms_watch.sh root@sms-line1.internal.lambdanum.com:/scripts/sms_watch.sh
# scp com.lambdanum.sms-watch.plist root@sms-line1.internal.lambdanum.com:/Library/LaunchDaemons/com.lambdanum.sms-watch.plist
