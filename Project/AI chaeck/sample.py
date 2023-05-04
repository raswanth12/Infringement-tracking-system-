import requests
import json
import tkinter as tk
from tkinter import filedialog

API_KEY = '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'


def is_ai_generated(text):
    url = 'https://api.openai.com/v1/engines/davinci/completions'

    headers = {
        'Content-Type': 'application/json',
        'Authorization': f'Bearer {API_KEY}',
    }

    prompt = f'Is the following text generated by an AI or not? Provide a simple answer.\\n\\n{text}\\n\\nAnswer:'

    data = {
        'prompt': prompt,
        'max_tokens': 10,
        'n': 1,
        'stop': None,
        'temperature': 0.5,
    }

    response = requests.post(url, headers=headers, data=json.dumps(data))
    response_json = response.json()

    if response.status_code == 200:
        ai_generated = response_json['choices'][0]['text'].strip().lower()
        return ai_generated == 'yes'
    else:
        print(f'Error: {response.status_code}')
        print(response_json)
        return None


def open_file():
    file_path = filedialog.askopenfilename(
        filetypes=[('Text Files', '*.txt'), ('All Files', '*.*')])
    with open(file_path, 'r', encoding='utf-8') as file:
        text = file.read()

    result = is_ai_generated(text)

    if result is not None:
        if result:
            result_label.config(
                text="The text seems to be generated by an AI.")
        else:
            result_label.config(
                text="The text does not seem to be generated by an AI.")
    else:
        result_label.config(
            text="Unable to determine if the text is AI-generated.")


if __name__ == '__main__':
    root = tk.Tk()
    root.title("AI Text Classifier")

    open_button = tk.Button(root, text="Open Text File", command=open_file)
    open_button.pack(padx=10, pady=10)

    result_label = tk.Label(root, text="")
    result_label.pack(padx=10, pady=10)

    root.mainloop()
