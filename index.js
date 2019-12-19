import express from 'express';
import { json } from 'body-parser';
import cors from 'cors';
import helmet from 'helmet';
import morgan from 'morgan';
import { join } from 'path';
import rfs from 'rotating-file-stream';
import dotenv from 'dotenv';
import logger from './src/logger/logger';
import connectDatabase from './src/configs/db.config';

/* istanbul ignore next */
dotenv.config();

// configure isProduction variable
const isProduction = process.env.NODE_ENV === 'production';
const port = process.env.PORT || 3000;

// defining the Express app
const app = express();

// adding Helmet to enhance your API's security
app.use(helmet());

// using bodyParser to parse JSON bodies into JS objects
app.use(json());

// enabling CORS for all requests
app.use(cors());

// create a rotating write stream
const accessLogStream = rfs('access.log', {
  interval: '1d', // rotate daily
  path: join(__dirname, 'log'),
});

// adding morgan to log HTTP requests
app.use(morgan('combined', { stream: accessLogStream }));

// connect to mongo
connectDatabase();

if (!isProduction) {
  // eslint-disable-next-line global-require
  app.use(require('errorhandler')());
}

app.get('/', (req, res) => {
  logger.info('GET /');
  res.send('App works!!!!!');
});

app.use('/api', require('./src/routes/routes').default);

// request to handle undefined or all other routes
app.get('*', (req, res) => {
  logger.info('GET undefined routes');
  res.send('App works!!!!!');
});

// Error handlers & middlewares
if (!isProduction) {
  app.use((err, req, res) => {
    res.status(err.status || 500);

    res.json({
      errors: {
        message: err.message,
        error: err,
      },
    });
  });
}

app.use((err, req, res) => {
  res.status(err.status || 500);

  res.json({
    errors: {
      message: err.message,
      error: {},
    },
  });
});

// starting the server
app.listen(port, () => {
  logger.info(`listening on port ${port}`);
});

// Export our app for testing purposes
export default app;
