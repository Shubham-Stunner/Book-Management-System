/**
 * Optional seed script. Mongo runs this automatically on first init.
 */
db = db.getSiblingDB("booksdb");
db.books.insertMany([
  { title: "Clean Code", author: "Robert C. Martin", isbn: "9780132350884", price: 34.99 },
  { title: "Effective Java", author: "Joshua Bloch", isbn: "9780134685991", price: 39.99 }
]);
