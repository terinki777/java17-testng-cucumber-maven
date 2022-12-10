#language:ru

  @test
  Функционал: Тестирование сервиса Helpdesk

    Сценарий: Создание тикета с высоким приоритетом

      * сгенерировать переменные
        | queue           | 1                |
        | due_date        | 2022-12-05       |
        | assigned_to     | admin            |
        | status          | 1                |
        | title           | test2746758758   |
        | description     | test             |
        | priority        | 2                |
        | submitter_email | test@mail.ru     |

      * создать запрос
        | method | path        | body              |
        | POST   | api/tickets | createTicket.json |

      * добавить header
        | Content-Type | application/json |

      * отправить запрос

      * статус код 201

      * извлечь данные
        | id | $.id |

      И сравнить значения
        | ${id} | != | null |


