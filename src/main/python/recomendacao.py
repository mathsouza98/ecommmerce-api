import pandas as pd
import numpy as np
import pymongo

import warnings
warnings.filterwarnings('ignore')

def recommend_products(user_id: str=None):
    # Abrindo conexão com o Mongo
    myclient = pymongo.MongoClient("mongodb://127.0.0.1:27017")
    mydb = myclient["ecommerce"]

    # Abrindo coleção da lista de produtos
    mycol_prod = mydb["product"]

    # Lendo dados da collection products e convertendo em DataFrame
    products = pd.DataFrame()
    for x in mycol_prod.find():
        products = products.append(pd.DataFrame.from_dict(x, orient='index').T)

    # Tratando dados
    products.rename(columns={'_id': 'productId'}, inplace=True)
    products.productId = products.productId.astype(str)

    if (user_id):
        # Abrindo coleção do Historico de Navegação
        mycol = mydb["userHistory"]

        #Lendo dados e convertendo em DataFrame
        user_history = pd.DataFrame()
        for x in mycol.find():
            aux = pd.Series(x['history']).apply(pd.Series)
            aux['userId'] = str(x['_id'])
            user_history = user_history.append(aux)

        # Matriz de produtos e suas respectivas visualizações por usuário
        final_user_rating = user_history.pivot_table(index='productId', \
                                                     columns='userId', \
                                                     values='visualization', \
                                                     fill_value=0)

        # Criando matriz de correlação entre os produtos (baseado na visualização de outros usuários)
        correlation_matrix = np.corrcoef(final_user_rating)

        # Produto que o usuario mais visitou
        productId = user_history[user_history.userId == user_id].sort_values('visualization') \
            .tail(1).productId.item()

        # Dicionario com o productId e seu respectivo indice numerico
        productId_numb = dict(zip(list(final_user_rating.index), \
                                  [i for i in range(len(final_user_rating.index))] \
                                  ))

        # Selecionando vetor de correlação do produto selecionado com os demais
        correlation_product_ID = correlation_matrix[productId_numb[productId]]

        # Selecionando os produtos recomendados
        df_recommend = pd.DataFrame({'id': final_user_rating.index, 'corr': correlation_product_ID})
        recommended = df_recommend.sort_values(by='corr').iloc[-6: -1].id.values

    else:
        # Ordenando e selecionando os 5 produtos mais visualizados
        products.rename(columns={'_id': 'productId'}, inplace=True)
        products.productId = products.productId.astype(str)
        recommended = products.sort_values(by='totalVisualization', ascending=False).head(5).productId.values

    # Pegando todos os atributos dos produtos selecionados e retornando um dicionário
    products_reindex = products.merge(pd.DataFrame({'productId': recommended}), on='productId')

    return products_reindex.to_dict(orient='records')
