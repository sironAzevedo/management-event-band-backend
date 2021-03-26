const { Model, DataTypes } = require('sequelize');

class Band extends Model {
  static init(sequelize) {
    super.init({
      name: DataTypes.STRING,
    }, {
      sequelize,
      tableName: 'bands',
    })
  }

  static associate(models) {
    this.belongsToMany(models.User, { foreignKey: 'band_id', through: 'user_bands', as: 'users' });
  }
}

module.exports = Band;