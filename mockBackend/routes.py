from flask import Flask, request, session, redirect
import urllib.request

app = Flask(__name__);

@app.route('/sms_received', methods = ['POST'])
def smsReceived():
    id = request.form.get('id');

    print(id);
    print(urllib.request.urlopen("http://sms-line1.internal.lambdanum.com/get_message.py?message_id={}".format(id)).read());
    return 'OK';
    
if __name__ == "__main__":
    app.run(port=5000, debug=False, threaded=False, host="0.0.0.0");
