export type Category = {
  id: string;
  name: string;
  slug: string;
};

export type Product = {
  id: string;
  name: string;
  description: string;
  price: number;
  category_id: string;
  images: string[];
  stock_status: 'in_stock' | 'out_of_stock';
  created_at: string;
};