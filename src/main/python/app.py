from flask import Flask, jsonify
from recomendacao import recommend_products

app = Flask(__name__)

# Define a rota onde a API será aplicada
@app.route("/", defaults={"user_id": None})
@app.route("/<string:user_id>")

def run_api(user_id: str):

    # Chama a função de recomendação e retorna uma lista com o id dos produtos recomendados
    recomendation = list(recommend_products(user_id))

    # Retorna um json com o output da função "recommend_products"
    return jsonify(productId=str(recomendation)), 200

# Inicia a aplicação
if __name__ == '__main__':
    app.run()