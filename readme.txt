Está branch foi criada para explicar um erro que ocorreu comigo num projeto simples

Erro Apresentado: "The project is using an incompatible version (AGP 8.12.2) of the Android Gradle plugin. Latest supported version is AGP 8.11.0 See Android Studio & AGP compatibility options."

Este projeto em Kotlin estava apresentando erro de Versão de Gradle, e não estava querendo rodar de jeito
nenhum devido a diferença de versões do gradle na minha Maquina em casa e a da Maquina da Fatec.
Por isso, após tanta procura, encontrei o arquivo que defina as versões dos componentes do projeto
incluindo a do Gradle(agp) que estava dando conflito com este projeto.
E para resolver este problema basta apenas trocar a versão do projeto para a versão inferior suportada
que o problema será resolvido.

Indo na Pasta Gradle Scripts (Fora da pasta "app") e abrindo o arquivo "libs.versions.toml, pode-se encontrar
as versões dos componentes

Neste caso a versão do projeto original é a 8.12.2 e a modificada para funcionar foi a 8.11.0
realizando essa mudança e sincronizando, todos os outros componentes serão atualizados automaticamente