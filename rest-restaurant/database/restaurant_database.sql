DROP TABLE IF EXISTS public.reservation_restaurant_table;
DROP TABLE IF EXISTS public.reservation;
DROP TABLE IF EXISTS public.restaurant_table;
DROP TABLE IF EXISTS public.menus;

CREATE TABLE public.menus (
    menu_id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    category character varying(50) NOT NULL,
    chefs_choice boolean,
    description character varying(500) NOT NULL,
    img_url character varying(300) NOT NULL,
    name character varying(50) NOT NULL,
    price numeric(38,2) NOT NULL
);

CREATE TABLE public.reservation (
    id uuid PRIMARY KEY,
    end_time timestamp(6) without time zone NOT NULL,
    number_of_people integer NOT NULL,
    reservee_last_name character varying(255) NOT NULL,
    reservee_phone_number character varying(255) NOT NULL,
    start_time timestamp(6) without time zone NOT NULL
);

CREATE TABLE public.reservation_restaurant_table (
    reservation_id uuid NOT NULL,
    table_id uuid NOT NULL,
    PRIMARY KEY (reservation_id, table_id)
);

CREATE TABLE public.restaurant_table (
    table_id uuid PRIMARY KEY,
    table_seats integer NOT NULL
);

INSERT INTO public.menus (menu_id, category, chefs_choice, description, img_url, name, price) VALUES ('6d71454c-d4a8-4333-8877-1eb43d1ca323', 'Pizza', true, 'Tomatensauce, Mozzarella, frisches Basilikum', 'https://images.unsplash.com/photo-1574071318508-1cdbab80d002?fm=jpg&q=60&w=3000&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8cGl6emElMjBtYXJnaGVyaXRhfGVufDB8fDB8fHww', 'Pizza Margherita', 10.90);
INSERT INTO public.menus (menu_id, category, chefs_choice, description, img_url, name, price) VALUES ('deb79307-84bf-4532-b967-be8e5d03c6e8', 'Pizza', false, 'Tomatensauce, Mozzarella und Salami', 'https://www.unileverfoodsolutions.pl/dam/global-ufs/mcos/NEE/calcmenu/recipes/PL-recipes/general/pizza-salami-cacciatore/main-header.jpg', 'Pizza Salami', 12.90);
INSERT INTO public.menus (menu_id, category, chefs_choice, description, img_url, name, price) VALUES ('e7cc1896-1e4e-4106-b119-7c8d667fd61a', 'Pizza', false, 'Tomatensauce, Mozzarella und Schinken', 'https://static.ah.nl/static/recepten/img_RAM_PRD202429_1224x900_JPG.jpg', 'Pizza Prosciutto', 13.50);
INSERT INTO public.menus (menu_id, category, chefs_choice, description, img_url, name, price) VALUES ('69857879-ed89-4fa8-ace3-25377ae8a221', 'Pizza', false, 'Mozzarella und frische Champignons', 'https://www.familienkost.de/images/pizza-funghi.jpg', 'Pizza Funghi', 12.50);
INSERT INTO public.menus (menu_id, category, chefs_choice, description, img_url, name, price) VALUES ('49f540f3-ff86-4d00-a1d4-7a6e0e2654bc', 'Pizza', true, 'Vier verschiedene Käsesorten', 'https://www.italianstylecooking.net/wp-content/uploads/2020/04/Pizza-quattro-formaggi-neu.jpg', 'Pizza Quattro Formaggi', 15.90);
INSERT INTO public.menus (menu_id, category, chefs_choice, description, img_url, name, price) VALUES ('330fd38b-b51f-40e8-8b85-dbc814c9a510', 'Pizza', true, 'Scharfe Salami und Chili', 'https://www.negroni.com/sites/default/files/styles/scale__1440_x_1440_/public/pizza_rustica.jpg.webp?itok=yOfpO3mx', 'Pizza Diavola', 14.90);
INSERT INTO public.menus (menu_id, category, chefs_choice, description, img_url, name, price) VALUES ('da4d3270-066c-40ec-a464-d995d0fdf750', 'Pizza', false, 'Thunfisch und rote Zwiebeln', 'https://fitaliancook.com/wp-content/uploads/2026/01/pizza-tonno-rezept-beitragsbild.jpg', 'Pizza Tonno', 14.50);
INSERT INTO public.menus (menu_id, category, chefs_choice, description, img_url, name, price) VALUES ('7183e0a4-30af-4abf-a3f5-d8abf0eeb1a3', 'Pizza', false, 'Gemüse-Mix mit Mozzarella', 'https://cdn.shopify.com/s/files/1/0191/9978/files/Pizza-Veggie-Supreme-blog.jpg?v=1652775259', 'Pizza Vegetariana', 13.90);
INSERT INTO public.menus (menu_id, category, chefs_choice, description, img_url, name, price) VALUES ('0b11ff32-562f-44db-8b12-73da0d4b831a', 'Pizza', true, 'Meeresfrüchte und Knoblauch', 'https://i.ytimg.com/vi/HX9i4fnqg_U/maxresdefault.jpg', 'Pizza Frutti di Mare', 17.90);
INSERT INTO public.menus (menu_id, category, chefs_choice, description, img_url, name, price) VALUES ('8e8e915f-ff2d-435a-a263-fdb4b3c36c17', 'Getränk', false, 'Klassische Coca-Cola 0.33L', 'https://www.designenlassen.de/blog/wp-content/uploads/2024/02/Design-ohne-Titel-57.png', 'Coca-Cola', 3.50);
INSERT INTO public.menus (menu_id, category, chefs_choice, description, img_url, name, price) VALUES ('9545611c-4751-4abd-8ace-b20982efb454', 'Getränk', false, 'Zuckerfreie Coca-Cola 0.33L', 'https://logowik.com/content/uploads/images/coca-cola-zero7222.logowik.com.webp', 'Coca-Cola Zero', 3.50);
INSERT INTO public.menus (menu_id, category, chefs_choice, description, img_url, name, price) VALUES ('f8573733-4aae-4371-b335-9e2439d3821e', 'Getränk', false, 'Zitronenlimonade 0.33L', 'https://logodix.com/logo/36934.png', 'Sprite', 3.50);
INSERT INTO public.menus (menu_id, category, chefs_choice, description, img_url, name, price) VALUES ('05ae51f8-9ccf-4e0f-a672-003ff31f3e72', 'Getränk', false, 'Orangenlimonade 0.33L', 'https://logo-marque.com/wp-content/uploads/2020/06/Fanta-Logo-2016-Pr%C3%A9sent.jpg', 'Fanta', 3.50);
INSERT INTO public.menus (menu_id, category, chefs_choice, description, img_url, name, price) VALUES ('0a038e5d-f689-4906-b7be-e20c19d52bea', 'Getränk', false, 'Mineralwasser 0.5L', 'https://images.seeklogo.com/logo-png/31/1/s-pellegrino-logo-png_seeklogo-317895.png', 'Mineralwasser', 2.90);
INSERT INTO public.menus (menu_id, category, chefs_choice, description, img_url, name, price) VALUES ('6c771fe7-c398-40d2-8092-48f7cc648cc0', 'Dessert', false, 'Hausgemachtes italienisches Tiramisu', 'https://www.giallozafferano.de/images/7-797/Tiramisu_650x433_wm.jpg', 'Tiramisu', 6.90);
INSERT INTO public.menus (menu_id, category, chefs_choice, description, img_url, name, price) VALUES ('d2ed3b10-16a5-4d83-98bf-fa5191812776', 'Dessert', false, 'Panna Cotta mit Beerensauce', 'https://img.chefkoch-cdn.de/rezepte/589331158388576/bilder/1510432/crop-960x540/panna-cotta.jpg', 'Panna Cotta', 5.90);
INSERT INTO public.menus (menu_id, category, chefs_choice, description, img_url, name, price) VALUES ('74fb5ed8-eaca-4390-a7ca-52a3dad467bd', 'Dessert', true, 'Warmer Schokoladenkuchen', 'https://www.alnatura.de/-/media/Images/Common/Recipe-Images/1/10/106/1066/106652_Veganer_Schokokuchen_Quelle_Alnatura_Foto_Brachat_1350.jpg?mw=960&hash=E95A0C68608B78B00A1031C3113F93CC', 'Schokoladenkuchen', 6.50);
INSERT INTO public.menus (menu_id, category, chefs_choice, description, img_url, name, price) VALUES ('f1d874b5-d125-4ca7-8074-17711b57414d', 'Dessert', false, 'Italienisches Vanilleeis', 'https://staticcookist.akamaized.net/wp-content/uploads/sites/21/2024/09/gelato-alla-vaniglia-still-life-1.jpg', 'Gelato Vaniglia', 4.90);


INSERT INTO public.reservation (id, end_time, number_of_people, reservee_last_name, reservee_phone_number, start_time) VALUES ('8b096cb6-c2f7-4911-9c30-b111aebb6e56', '2026-06-17 13:27:00', 3, 'Fidan', '046 877 56 89', '2026-06-17 11:27:00');
INSERT INTO public.reservation (id, end_time, number_of_people, reservee_last_name, reservee_phone_number, start_time) VALUES ('fe27d05b-d216-449d-bb8b-834085de7188', '2026-06-25 19:28:00', 5, 'Müller', '076 456 54 35', '2026-06-25 17:28:00');
INSERT INTO public.reservation (id, end_time, number_of_people, reservee_last_name, reservee_phone_number, start_time) VALUES ('ad3973c5-42f3-4859-be48-775b57b3ce0a', '2026-07-04 23:29:00', 8, 'Harald', '076 435 46 43', '2026-07-04 21:29:00');


INSERT INTO public.reservation_restaurant_table (reservation_id, table_id) VALUES ('8b096cb6-c2f7-4911-9c30-b111aebb6e56', '9d8cf702-e266-4d5b-bd7f-19a3611bf315');
INSERT INTO public.reservation_restaurant_table (reservation_id, table_id) VALUES ('fe27d05b-d216-449d-bb8b-834085de7188', 'cab08333-8030-4b70-8706-d8d268d30f39');
INSERT INTO public.reservation_restaurant_table (reservation_id, table_id) VALUES ('ad3973c5-42f3-4859-be48-775b57b3ce0a', 'cdeec62c-b628-4a7a-9235-73b52fd4730f');


INSERT INTO public.restaurant_table (table_id, table_seats) VALUES ('5d30b2da-baa7-429f-86cf-37d328d430da', 3);
INSERT INTO public.restaurant_table (table_id, table_seats) VALUES ('b00f2b73-5ee7-49ad-b2aa-60a138a4be58', 4);
INSERT INTO public.restaurant_table (table_id, table_seats) VALUES ('ae95a977-7505-4b16-9627-2487255bc75f', 4);
INSERT INTO public.restaurant_table (table_id, table_seats) VALUES ('cab08333-8030-4b70-8706-d8d268d30f39', 6);
INSERT INTO public.restaurant_table (table_id, table_seats) VALUES ('fb1c1010-ce3b-4d77-8afb-812fbc758b0e', 8);
INSERT INTO public.restaurant_table (table_id, table_seats) VALUES ('cdeec62c-b628-4a7a-9235-73b52fd4730f', 8);
INSERT INTO public.restaurant_table (table_id, table_seats) VALUES ('9d8cf702-e266-4d5b-bd7f-19a3611bf315', 2);
INSERT INTO public.restaurant_table (table_id, table_seats) VALUES ('86e5c773-a01d-4aea-8701-7ba742afa0f9', 2);


ALTER TABLE ONLY public.reservation_restaurant_table
    ADD FOREIGN KEY (table_id) REFERENCES public.restaurant_table(table_id);

ALTER TABLE ONLY public.reservation_restaurant_table
    ADD FOREIGN KEY (reservation_id) REFERENCES public.reservation(id);
