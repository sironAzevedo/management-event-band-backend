# management-event-band-services

Comandos:

Inicializar o projeto: yarn init -y
Instalando libs: yarn add express pg pg-hstore sequelize sequelize-cli -D jsonwebtoken cors

Executar a aplicação local: yarn dev

Criando Tabelas com Sequelize:
Criando migration: yarn sequelize migration:create --name=create-users
Criando tabela com o migration: yarn sequelize db:migrate
rollback com miration: yarn sequelize db:migrate:undo

