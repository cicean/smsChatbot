from flask import Flask, request, session, redirect;
import urllib.request;
import json;

app = Flask(__name__);

@app.route('/sms_received', methods = ['POST'])
def smsReceived():
    id = request.form.get('id');

    print(id);
    message = json.loads(urllib.request.urlopen("http://sms-line1.internal.lambdanum.com/get_message.py?message_id={}".format(id)).read());
    print (urllib.request.urlopen("http://sms-line1.internal.lambdanum.com/send_message.py?destination={}&message={}".format(message['chat_identifier'].strip('+'),"Thank%20you,%20your%20message%20has%20been%20received.%20%23{}.".format(message['id']))).read());
    return 'OK';
    
if __name__ == "__main__":
    app.run(port=5000, debug=False, threaded=False, host="0.0.0.0");
