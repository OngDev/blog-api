import mongoose from 'mongoose';
import logger from '../logger/logger';

/* istanbul ignore next */
const connectDatabase = () => {
  mongoose.Promise = global.Promise;
  // Connecting to the database
  mongoose
    .connect(`mongodb://${process.env.MONGO_HOST}:${process.env.MONGO_PORT}/${process.env.MONGO_DB}`, {
      useNewUrlParser: true,
      useUnifiedTopology: true,
      useCreateIndex: true,
      useFindAndModify: false,
    })
    .then(() => {
      logger.info('Successfully connected to the database');
    })
    .catch((err) => {
      logger.info(`Could not connect to the database. Exiting now...\n${err}`);
      process.exit();
    });
};

export default connectDatabase;
