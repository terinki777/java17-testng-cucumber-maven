#language:ru

  @test
  Функционал: Тестирование функционала сервиса Helpdesk

    Сценарий: Негативная проверка: перевод статуса тикета из "Закрыт" в "Открыт".

      * сгенерировать переменные
        | queue           | 1                |
        | due_date        | 2022-12-05       |
        | assigned_to     | admin            |
        | status          | 4                |
        | title           | test             |
        | description     | test             |
        | priority        | 2                |
        | submitter_email | test@mail.ru     |

#      создать тикет
      * создать запрос
        | method | path         | body              |
        | POST   | api/tickets | createTicket.json |

      * добавить header
        | Accept       | application/json |
        | Content-Type | application/json |

      * отправить запрос

      * статус код 201

      * извлечь данные
        | id | $.id |



#      авторизация
      * создать запрос
        | method | path       | body           |
        | POST   | api/login | loginUser.json |

      * добавить header
        | Accept       | application/json |
        | Content-Type | application/json |

      * отправить запрос

      * статус код 200
      * извлечь данные
        | token | $.token |

#      перевод из статуса закрыт в открыт
        * сгенерировать переменные
        | status | 1 |

      * создать запрос
        | method | path               | body              |
        | PUT    | api/tickets/${id} | createTicket.json |

      * добавить header
        | Content-Type  | application/json |
        | Authorization | Token ${token}   |

      * отправить запрос
      * статус код 422





