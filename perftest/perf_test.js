import http from 'k6/http';
import { check } from 'k6';

export let options = {
  vus: 10, // virtual users
  duration: '30s',
};

export default function () {
  let res = http.get('http://localhost:8080/fibonacci?maxIterations=10');
  check(res, {
    'status is 200': (r) => r.status === 200,
    'body contains Fibonacci series': (r) => r.body.includes('0, 1, 1, 2, 3, 5, 8, 13, 21, 34'),
  });
}
