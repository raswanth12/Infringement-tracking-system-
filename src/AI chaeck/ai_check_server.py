import json
import requests
from flask import Flask, request, jsonify

app = Flask(__name__)

API_KEY = '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'


def is_ai_generated(text):
    # ... (same as before)


@app.route('/ai_check', methods=['POST'])
def ai_check():
    text = request.json.get('text', '')
    if not text:
        return jsonify({'error': 'No text provided'}), 400

    result = is_ai_generated(text)
    if result is not None:
        return jsonify({'ai_generated': result})
    else:
        return jsonify({'error': 'Unable to determine if the text is AI-generated'}), 500


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
